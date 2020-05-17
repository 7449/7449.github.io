---
layout:     post
title:      "Android Q 存储变化的适配"
subtitle:   "版本适配"
date:       2020-5-17
tags:
    - android
---

* 本文不对Q以下的版本存储再进行说明，只简单讲下在`Android Q`的版本上如何对存储进行适配

  在 `Android Q`的时候系统开始对存储这块动手,进行了沙盒机制,也就是说在10版本的时候直接对`File`进行操作会直接报错
  
  当然`google`在`Q`版本对`App`进行了适配,在清单文件中添加属性可以暂时不使用沙盒机制,这里对这个方法不进一步说明,因为
  
  在`Android 11`的时候会强制进行沙盒机制,这点非常不错,很多`App`都是在`sd`目录随便创建文件夹,过段时间系统目录会很乱
  
* 这里以`uCrop`为例,因为直接对`externalCacheDir`或者`cacheDir`进行读写是不需要权限的,这些数据会随着`app`卸载而删除

  防君子不防小人的做法,随着版本升级,`uCrop`如果`outPutUri`为其他路径,会直接报错没有权限
  
  官方示例是直接在`cacheDir`下操作的,所以没有问题,这里讲一个类似于曲线救国的例子
  
示例以`externalCacheDir`为例,先对低版本进行简单适配

    //可以添加对mkdirs的判断
    fun Context.lowerVersionFile(fileName: String, relativePath: String = Environment.DIRECTORY_DCIM): File {
        val path: String = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            @Suppress("DEPRECATION")
            Environment.getExternalStoragePublicDirectory(relativePath).path
        } else {
            if (externalCacheDir == null) cacheDir.path else externalCacheDir?.path.orEmpty()
        }
        return File(path, fileName)
    }

接下来对路径进行版本适配

    //这里也很简单
    //Q以上直接时候用externalCacheDir
    //如果低版本路径不存在则直接使用缓存路径
    //如果自定义路径则自行创建文件
    fun Context.cropPathToUri(
            cropPath: String?,
            cropName: String,
            cropNameSuffix: String,
            relativePath: String
    ): Uri {
        val fileName = "$cropName.$cropNameSuffix"
        val file: File = when {
            hasQExpand() -> File(externalCacheDir, fileName)
            cropPath.isNullOrEmpty() -> lowerVersionFile(fileName, relativePath)
            else -> galleryPathFile(cropPath, fileName)
        }
        return Uri.fromFile(file)
    }

这里有个问题,高版本裁剪是在缓存路径下

    file:///storage/emulated/0/Android/data/packageName/cache/xxxxx.jpg
    
如果用户裁剪想直接保存在图库中这种高版本适配是满足不了这个需求，由于`uCrop`没有适配这个方法,所以这里自己改进下逻辑

这里的思路是使用`fileStream`在高版本中创建裁剪图片在图库中

这里的`cropUri`则是`content://media/external/images/media/id`格式的

这样可以实现一种裁剪的另类设置保存路径，至于缓存里面的裁剪文件可删可不删,看需求而定

这里没有使用`uri to bitmap`是因为作者在实现的过程中发现这种方法压缩的厉害，如果想实现类似于`copy`

的方法还是`fileStream`比较好一点

    fun Context.saveCropToGalleryLegacy(
            cropUri: Uri,
            cropName: String,
            cropNameSuffix: String,
            relativePath: String
    ): Uri? {
        if (!hasQExpand()) {
            return null
        }
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$cropName.$cropNameSuffix")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        val uri: Uri = insertImageUriExpand(contentValues)
        val outStream: OutputStream = contentResolver.openOutputStream(uri) ?: return null
        val inStream: InputStream = contentResolver.openInputStream(cropUri) ?: return null
        outStream.use { out -> inStream.use { input -> input.copyTo(out) } }
        return uri
    }