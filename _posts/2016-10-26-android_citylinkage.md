---
layout:     post
title:      "Android_省市县三级联动"
subtitle:   "通过解析xml文件实现android上面的省市县三级联动。"
date:       2016-10-26
tags:
    - wheelView
    - android
---


## json数据展示

#### 代码示例

[view-number-picker](https://github.com/7449/AndroidDevelop/blob/develop/ViewSample/NumberPicker)

#### 效果图

![_config.yml]({{ site.baseurl }}/assets/screenshot/16/number_picker.gif)

#### 代码相关

和下面不同的是这次用`DialogFragment`实现，并且使用了`Builder`模式

使用时：

        new EasyCityView
                .Builder(this)
                .setCancelable(true)
                .setTitle("请选择所在城市")
                .setProvinceName("陕西省") // 默认选中省
                .setCityName("西安市") //默认选中市
                .setAreaName("雁塔区") //默认选中区
                .setDividerColor(R.color.colorPrimary)
                .setSelectTextColor(R.color.colorPrimary)
                .show(getSupportFragmentManager(), "city");

主要就是为`DialogFragment`设置了一个从下到上出现的动画，并且消除了`dialog`左右留白的问题

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow()
                    .setLayout(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }



## xml数据展示

#### 代码示例

[view-wheel](https://github.com/7449/AndroidDevelop/blob/develop/ViewSample/Wheel)

#### 效果图

![_config.yml]({{ site.baseurl }}/assets/screenshot/16/wheelview.gif)

#### 核心代码

通过`WheelView`实现了数据的展示，然后用`popupwindow`实现。

这里就贴一点核心代码，如果想查看所有的代码或者Demo，请看代码示例

主要的就是XmlManager解析的时候需要for循环遍历所有的数据

			// 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {

                mCurrentProviceName = provinceList.get(0).getName();
                List<CityResult> cityList = provinceList.get(0).getCityList();

                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictResult> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
                mProvinceDatas = new String[provinceList.size()];

                for (int i = 0; i < provinceList.size(); i++) {

                    // 遍历所有省的数据
                    mProvinceDatas[i] = provinceList.get(i).getName();
                    cityList = provinceList.get(i).getCityList();
                    String[] cityNames = new String[cityList.size()];


                    for (int j = 0; j < cityList.size(); j++) {

                        // 遍历省下面的所有市的数据
                        cityNames[j] = cityList.get(j).getName();
                        List<DistrictResult> districtList = cityList.get(j).getDistrictList();
                        String[] distrinctNameArray = new String[districtList.size()];
                        DistrictResult[] distrinctArray = new DistrictResult[districtList.size()];


                        for (int k = 0; k < districtList.size(); k++) {

                            // 遍历市下面所有区/县的数据
                            DistrictResult districtResult = new DistrictResult(districtList.get(k).getName(), districtList.get(k).getZipcode());

                            // 区/县对于的邮编，保存到mZipcodeDatasMap
                            mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                            distrinctArray[k] = districtResult;
                            distrinctNameArray[k] = districtResult.getName();
                        }

                        // 市-区/县的数据，保存到mDistrictDatasMap
                        mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }

                    // 省-市的数据，保存到mCitisDatasMap
                    mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
                }
            }

>然后通过WheelView的检测滑动方法更新页面的滑动数据

		 /**
	     * 根据当前的市，更新区WheelView的信息
	     */
	    public void updateAreas(int cityCurrent) {
	        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
	        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[cityCurrent];
	        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
	        if (areas == null) {
	            areas = new String[]{""};
	        }
	        if (xmlManagerInterface != null) {
	            xmlManagerInterface.updateAreas(areas);
	        }
	        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
	    }
	
	    /**
	     * 根据当前的省，更新市WheelView的信息
	     */
	    public void updateCities(int provinceCurrent) {
	        mCurrentProviceName = mProvinceDatas[provinceCurrent];
	        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
	        if (cities == null) {
	            cities = new String[]{""};
	        }
	        if (xmlManagerInterface != null) {
	            xmlManagerInterface.updateCities(cities);
	        }
	    }
	
	    public void update(int newValue) {
	        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
	        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
	    }



>通过接口获取选中的数据，接口获取的数据是直接给了当前的activity

	public interface XmlPopupWindowInterface {
	    void setData(String currentProviceName, String currentCityName, String currentDistrictName);
	}
