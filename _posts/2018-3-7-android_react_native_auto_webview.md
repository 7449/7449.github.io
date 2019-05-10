---
layout:     post
title:      "RN自适应高度WebView"
subtitle:   "代码"
date:       2018-3-7
tags:
    - android
    - React-Native
---

    export default class AutoWebView extends Component {
    
        state = {
            webViewHeight: Number
        };
    
        static defaultProps = {
            autoHeight: true,
        };
    
        constructor(props: Object) {
            super(props);
            this.state = {
                webViewHeight: this.props.defaultHeight
            };
            this.onMessage = this.onMessage.bind(this);
        }
    
        onMessage(e) {
            this.setState({
                webViewHeight: parseInt(e.nativeEvent.data)
            });
        }
    
        injectedScript = () => {
            setTimeout(function waitForBridge() {
                if (window.postMessage.length !== 1) {
                    setTimeout(waitForBridge, 2000);
                } else {
                    let height = 0;
                    if (document.documentElement.clientHeight > document.body.clientHeight) {
                        height = document.documentElement.clientHeight;
                    } else {
                        height = document.body.clientHeight
                    }
                    postMessage(height)
                }
            }, 2000);
        };
    
    
        render() {
            const newWidth = this.props.width || '100%';
            const height = this.props.autoHeight ? this.state.webViewHeight : this.props.defaultHeight;
            return (
                <View style={[{width: newWidth}, this.props.style, {height: height}]}>
                    {isNull(height) ? <ActivityIndicator
                        animating={true}
                        color={'red'}
                        size="large"
                    /> : null}
                    <WebView
                        injectedJavaScript={'(' + String(this.injectedScript) + ')();'}
                        scrollEnabled={this.props.scrollEnabled || false}
                        onMessage={this.onMessage}
                        javaScriptEnabled={true}
                        automaticallyAdjustContentInsets={true}
                        {...this.props}
                        style={this.props.style}
                    />
                </View>
    
            )
        }
    }
