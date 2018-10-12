---
layout:     post
title:      "Android面试总结--java部分"
subtitle:   "Android面试总结--java部分"
date:       2018-10-11
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
    - android
    - java
---

## 接口

 1. 接口中的字段全部默认为 public static类型。
 2. 接口中的方法全部默认为 public类型。
 3. 接口中可以申明内部类，而默认为public static，正因为是static，只是命名空间属于接口，代码逻辑不属于接口。所以不违法接口定义。
 4. 接口本身可以申明为public或者缺省。
 5. 抽象类继承自某接口。如果在抽象类中实现了父类（接口）中的方法，在其子类可以不用实现，否则在子类必须实现。

#### 接口实例化

接口无法直接创建对象，接口是一个特殊的抽象类，它里面所有的方法都是抽象的。
当一个类实现了这个接口，那么这个类是可以创建对象的。
这个类实现了这个接口，相当于创建了这个接口的子类，接口就是这个类的父类，
而父类引用是可以指向子类的，我们创建的不是接口对象，而是实现了该接口的子类的对象

    List<String> list=new ArrayList<String>(); 

`List`是一个接口，`ArrayList`是`List`接口的实现类，这就是所谓的面向接口编程

## final

###### 将方法声明为`final`有两个原因

1. 就是说明你已经知道这个方法提供的功能已经满足你要求，不需要进行扩展，并且也不允许任何从此类继承的类来覆写这个方法，但是继承仍然可以继承这个方法，也就是说可以直接使用。
2. 就是允许编译器将所有对此方法的调用转化为`inline`调用的机制，它会使你在调用`final`方法时，直接将方法主体插入到调用处，而不是进行例行的方法调用，例如保存断点，压栈等，
这样可能会使你的程序效率有所提高，然而当你的方法主体非常庞大时，或你在多处调用此方法，那么你的调用主体代码便会迅速膨胀，可能反而会影响效率，所以你要慎用`final`进行方法定义。

## HashTable HashMap

1. 继承不同
 
        public class HashTable extends Dictionary implements Map
        public class HashMap extends AbstractMap implements Map

2. `HashTable`中的方法是同步的，而`HashMap`中的方法在缺省情况下是非同步的。在多线程并发的环境下，可以直接使用`HashTable`，但是要使用`HashMap`的话就要自己增加同步处理了。
3. `HashTable`中，`key`和`value`都不允许出现`null`值。在`HashMap`中，`null`可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为`null`。当get()方法返回`null`值时，即可以表示`HashMap`中没有该键，也可以表示该键所对应的值为`null`。
因此，在`HashMap`中不能由`get()`方法来判断`HashMap`中是否存在某个键， 而应该用`containskey()`方法来判断。 两个遍历方式的内部实现上不同。
4. `HashTable`、`HashMap`都使用了 `Iterator`。而由于历史原因，`HashTable`还使用了`Enumeration`的方式 。 哈希值的使用不同，`HashTable`直接使用对象的`hashCode`。
而`HashMap`重新计算`hash`值。
5. `HashTable`和`HashMap`它们两个内部实现方式的数组的初始大小和扩容的方式。`HashTable`中`hash`数组默认大小是11，增加的方式是old*2+1。`HashMap`中`hash`数组的默认大小是16，而且一定是2的指数。

#### Iterator Enumeration

1. 函数接口不同 `Enumeration`只有2个函数接口。通过`Enumeration`，我们只能读取集合的数据，而不能对数据进行修改。`Iterator`只有3个函数接口。
`Iterator`除了能读取集合的数据之外，也能数据进行删除操作。
2. `Iterator`支持`fail-fast`机制，而`Enumeration`不支持。 `Enumeration` 是`JDK1.0`添加的接口。使用到它的函数包括`Vector`、`HashTable`等类，
这些类都是`JDK 1.0`中加入的，`Enumeration`存在的目的就是为它们提供遍历接口。`Enumeration`本身并没有支持同步，而在`Vector`、`HashTable`实现`Enumeration`时，添加了同步。
而`Iterator`是`JDK1.2`才添加的接口，它也是为了`HashMap`、`ArrayList`等集合提供遍历接口。
`Iterator`是支持`fail-fast`机制的：当多个线程对同一个集合的内容进行操作时，就可能会产生`fail-fast`事件。

## ArrayLis LinkedList

`ArrayList`初试大小为10，大小不够会调用`grow`扩容：length = length + (length >> 1)

`LinkedList`中Node first,last。分别指向头尾

`ArrayList`和`LinkedList`在性能上各 有优缺点，都有各自所适用的地方，总的说来可以描述如下：

1. 对`ArrayList`和`LinkedList`而言，在列表末尾增加一个元素所花的开销都是固定的。对`ArrayList`而言，
主要是在内部数组中增加一项，指向所添加的元素，偶尔可能会导致对数组重新进行分配；而对`LinkedList`而言，这个开销是 统一的，分配一个内部`Entry`对象。
2. 在`ArrayList`的中间插入或删除一个元素意味着这个列表中剩余的元素都会被移动；而在`LinkedList`的中间插入或删除一个元素的开销是固定的。
3. `LinkedList`不 支持高效的随机元素访问。
4. `ArrayList`的空间浪费主要体现在在`list`列表的结尾预留一定的容量空间，而`LinkedList`的空间花费则体现在它的每一个元素都需要消耗相当的空间。可以这样说：当操作是在一列 数据的后面添加数据而不是在前面或中间,并且需要随机地访问其中的元素时,使用`ArrayList`会提供比较好的性能；当你的操作是在一列数据的前面或中 间添加或删除数据,并且按照顺序访问其中的元素时,就应该使用`LinkedList`了。

