---
layout:     post
title:      "ViewBinding在Kotlin中的封装"
subtitle:   "废弃-kotlin-android-extensions"
date:       2021-1-4
tags:
    - kotlin
    - ViewBinding
---

# Activity

    inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
            lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
                val invoke = bindingInflater.invoke(layoutInflater)
                setContentView(invoke.root) //可选
                invoke
            }
            
## Activity中使用

    private val viewBind by viewBinding(ActivityBinding::inflate)
    
    viewBind.viewName
    
# Fragment

## 添加部分依赖

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    
## 创建生命周期管理

    class FragmentViewBindingDelegate<T : ViewBinding>(
        val fragment: Fragment,
        val viewBindingFactory: (View) -> T
    ) : ReadOnlyProperty<Fragment, T> {
        private var binding: T? = null
    
        init {
            fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner: LifecycleOwner? ->
                        viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                            override fun onDestroy(owner: LifecycleOwner) {
                                binding = null
                            }
                        })
                    }
                }
            })
        }
    
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            val binding = binding
            if (binding != null) {
                return binding
            }
            val lifecycle = fragment.viewLifecycleOwner.lifecycle
            if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
            }
            return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
        }
    }
    
## 封装
    
    fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
            FragmentViewBindingDelegate(this, viewBindingFactory)
            
## Fragment中使用
 
在`Fragment`中使用需要注意传入布局Id,因为在代理`getValue`的时候会获取`requireView`

    class MainFragment : Fragment(R.layout.fragment_main) {
    
        companion object {
            fun newInstance(): MainFragment {
                return MainFragment()
            }
        }
    
        private val viewBind by viewBinding(FragmentMainBinding::bind)
    }