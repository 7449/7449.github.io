---
layout:     post
title:      "RN中使用Realm数据库"
subtitle:   "RN中使用Realm数据库"
date:       2018-1-15
author:     "y"
header-mask: 0.3
header-img: "img/header_rn.png"
catalog: true
tags:
    - android
    - React-Native
---

## update

最后还是使用了 Redux 和 persist 解决了持久化数据的问题

## 存储的几种方式

* AsyncStorage

* Realm

`AsyncStorage`不需要`npm`或者`yarn`，直接`import`即可，并且操作是异步的。
这里不对`AsyncStorage`做解释。

有兴趣的可以看看`RN中文网`封装的一个组件[react-native-storage](https://github.com/sunnylqm/react-native-storage)

## Realm

优点不用多说,而且跨平台

Java，Objective-C，Swift，React Native，Xamarin，都支持

第一步当时然初始化一个`RN`项目

`react-native init ExampleRealm`

然后进入到初始化好的项目中执行

  `yarn`  
  
  `npm install --save realm`

可能会有一点慢,等一会就好了,好了之后执行

`react-native link realm`

将`Realm`链接到自己的项目里面

如果没有问题的话

`android/settings.gradle`

`android/app/build.gradle`

`MainApplication.java`

这三个文件会有些许变动,如果没有变动,请参考[Realm](https://realm.io/docs/javascript/latest/)



接下来就可以在`RN`项目中使用`Realm`了


## 简单使用

>将以下代码复制到App.js里面，如果没有问题,打开一次App就会看到数据库的Size+1，如果Reload效果也是一样的

    import React, {Component} from 'react';
    import {
        StyleSheet,
        Text,
        View,
    } from 'react-native';
    import Realm from 'realm';
    
    const ExampleSchema = {
        name: 'Example',
        properties: {
            example: 'string',
        }
    };
    
    export default class App extends Component<{}> {
        render() {
            let realm = new Realm({schema: [ExampleSchema]});
            let results = realm.objects('Example');
            realm.write(() => {
                realm.create('Example', {example: 'example'});
            });
            return (
                <View style={styles.container}>
                    <Text>{'Realm Example表个数' + results.length}</Text>
                </View>
            );
        }
    }
    
    const styles = StyleSheet.create({
        container: {
            flex: 1,
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: '#F5FCFF',
        },
        instructions: {
            marginTop: 30,
            textAlign: 'center',
            color: '#333333',
            marginBottom: 5,
        },
    });


## 封装

>起个realm文件夹,里面分别是schema，realm文件夹

* schema

        export const ExampleSchema = {
            name: 'Example',
            properties: {
                example: 'string',
            },
        };
        
* realm

        import Realm from 'realm';
        import {ExampleSchema} from "../schema/Schema";
        
        const realm = new Realm({
            schema: [ExampleSchema],
        });
        
        export function getExample() {
            return realm.objects('Example');
        }
        
        export function putExample(example) {
            realm.write(() => {
                realm.create('Example', example);
            });
        }
        
        export function updateExample(example) {
            realm.write(() => {
                realm.create('Example', example, true);
            });
        }
        
        export function deleteExample(example) {
            realm.write(() => {
                realm.delete(example);
            });
        }

这个时候`App.js`变得非常简单,只需要导入封装好的组件,直接增删改查即可

    import React, {Component} from 'react';
    import {
        StyleSheet,
        Text,
        View,
    } from 'react-native';
    import {getExample, putExample} from "./js/realm/manager/Realm";
    
    export default class App extends Component<{}> {
        render() {
            putExample({example: 'example'});
            return (
                <View style={styles.container}>
                    <Text>{'Realm Example表个数  ' + getExample().length}</Text>
                </View>
            );
        }
    }
    
    const styles = StyleSheet.create({
        container: {
            flex: 1,
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: '#F5FCFF',
        },
        instructions: {
            marginTop: 30,
            textAlign: 'center',
            color: '#333333',
            marginBottom: 5,
        },
    });
    
最后附上Example链接 

[ExampleRealm](https://github.com/7449/AndroidDevelop/tree/studio3/ExampleRealm)


