---
layout:     post
title:      "RN打包Apk"
subtitle:   "遇到的问题"
date:       2018-2-27
author:     "y"
header-mask: 0.3
header-img: "img/header_rn.png"
catalog: true
tags:
    - android
    - React-Native
---

> 加载本地html debug版正常，release显示空白

android要把`html`放在`assets`目录下才能正常加载，但是放在这里无法使用 `hot code`,所以加载的时候需要一个判断

                        <WebView
                            ref={'WebView'}
                            automaticallyAdjustContentInsets={false}
                            source={__DEV__ ? require('../../Simple.html') : {uri: 'file:///android_asset/Simple.html'}}
                            javaScriptEnabled={true}
                            domStorageEnabled={true}
                            startInLoadingState={true}/>

## 打开崩溃

正常打包成功，但是打开就崩溃，而且Bundle是正常生成的，报错日志为

    super expression must either be null or a function not string...
    
由于项目中大量使用了自己的封装组件,慢慢排查之后怀疑是自己封装的组件问题.
第一次找到问题还是错误的方向...，不过幸好最后找到了正确的方向


在使用自定义组件的时候，刚开始是这么定义的:

    export default class StatusView extends View {
        render() {
            const {status, onClickListener} = this.props;
            switch (status) {
                case LOADING:
                    return getStatusLoadingView();
                case ERROR:
                    return getStatusErrorView(onClickListener);
                case EMPTY:
                    return getStatusEmptyView(onClickListener);
                default:
                    return this.props.children;
            }
        }
    }
 
直接继承的`View`,这样在测试版中是可以正常运行的，但是不知道为什么在正式版中无法运行，必须要继承`Component`


    export default class HxbStatusView extends Component {
        render() {
            const {status, onClickListener} = this.props;
            switch (status) {
                case LOADING:
                    return getStatusLoadingView();
                case ERROR:
                    return getStatusErrorView(onClickListener);
                case EMPTY:
                    return getStatusEmptyView(onClickListener);
                default:
                    return this.props.children;
            }
        }
    }
