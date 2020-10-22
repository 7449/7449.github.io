---
layout:     post
title:      "Kotlin在ViewHolder的缓存处理"
subtitle:   "基础重温-kotlin-android-extensions"
date:       2020-10-22
tags:
    - kotlin
---

## 重新梳理下kotlin-ext

启用,这里使用的是as4.+，插件方式有所改变，老版本还是`apply`

    plugins {
        id 'kotlin-android-extensions'
    }

先看下在`Activity`中如何使用`kotlin-android-extensions`

       import android.app.Activity
       import android.os.Bundle
       //主要是引入这句，但是布局如果同名Id过多，容易出现异常
       import kotlinx.android.synthetic.main.activity_main.*

       class MainActivity : Activity() {
           override fun onCreate(savedInstanceState: Bundle?) {
               super.onCreate(savedInstanceState)
               setContentView(R.layout.activity_main)
               textView.text = "Kotlin"
           }
       }

编译后的`java`文件如下

    public final class MainActivity extends Activity {
       private HashMap _$_findViewCache;

       protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          this.setContentView(1300000);
          TextView var10000 = (TextView)this._$_findCachedViewById(id.textView);
          Intrinsics.checkNotNullExpressionValue(var10000, "textView");
          var10000.setText((CharSequence)"Kotlin");
       }

       public View _$_findCachedViewById(int var1) {
          if (this._$_findViewCache == null) {
             this._$_findViewCache = new HashMap();
          }

          View var2 = (View)this._$_findViewCache.get(var1);
          if (var2 == null) {
             var2 = this.findViewById(var1);
             this._$_findViewCache.put(var1, var2);
          }

          return var2;
       }

       public void _$_clearFindViewByIdCache() {
          if (this._$_findViewCache != null) {
             this._$_findViewCache.clear();
          }

       }
    }

其中`fragment`同理，都是创建了一个`HashMap`用来存储`View`,kotlin提供了三种方式

分别是`SPARSE_ARRAY` `HASH_MAP` `NO_CACHE` 默认为`HASH_MAP`

作者刚开始在`ViewHolder`中使用时，直接`holder.itemView.id`,这样也可以使用，但是直接使用的`findViewById`，没有缓存处理
所以当时也没有深入研究，就先使用之前封装好的`ViewHolder`

现在记录下如何在`ViewHolder`中带缓存使用`kotlin`这一个特性

## ViewHolder

在`gradle`中配置实验性特性

    androidExtensions {
        experimental = true
    }

主要是继承`LayoutContainer`

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View?
            get() = itemView
    }

其中`containerView`是目标缓存`View`，使用`ViewHolder`，这里就给予`itemView`

其编译如下,可以看到，IDE已经为我们自动生成了缓存


       public static final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements LayoutContainer {
          private HashMap _$_findViewCache;

          @NotNull
          public View getContainerView() {
             View var10000 = this.itemView;
             Intrinsics.checkNotNullExpressionValue(var10000, "itemView");
             return var10000;
          }

          public ViewHolder(@NotNull View itemView) {
             Intrinsics.checkNotNullParameter(itemView, "itemView");
             super(itemView);
          }

          public View _$_findCachedViewById(int var1) {
             if (this._$_findViewCache == null) {
                this._$_findViewCache = new HashMap();
             }

             View var2 = (View)this._$_findViewCache.get(var1);
             if (var2 == null) {
                View var10000 = this.getContainerView();
                if (var10000 == null) {
                   return null;
                }

                var2 = var10000.findViewById(var1);
                this._$_findViewCache.put(var1, var2);
             }

             return var2;
          }

          public void _$_clearFindViewByIdCache() {
             if (this._$_findViewCache != null) {
                this._$_findViewCache.clear();
             }

          }
       }

## 示警

如果使用缓存一定不要使用`holder.itemView.id`，而是直接使用`holder.id`，否则还是没有使用到缓存

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text
        holder.itemView.text
    }

如上代码，编译后如下,如果使用`item.text`则直接调用`findViewById`

       public void onBindViewHolder(@NotNull Adapter.ViewHolder holder, int position) {
          TextView var10000 = (TextView)holder._$_findCachedViewById(id.text);
          View var10001 = holder.itemView;
          TextView var3 = (TextView)var10001.findViewById(id.text);
       }

其实这里也很好区分

如果使用`holder.id`,导入的布局则是这个格式

    import kotlinx.android.synthetic.main.layoutId.*

如果使用`holder.itemView.id`,导入的布局则是这个格式,比上面的多了个view

    import kotlinx.android.synthetic.main.layoutId.view.*

## 小提示

可以在`ViewHolder`写一个方法，然后在`onBindViewHolder`调用这个方法就行，然后在方法中直接就可以使用id,而不用每次都`holder.id`