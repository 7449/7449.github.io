---
layout:     post
title:      "Android_ExpandableList_QQ好友列表效果"
subtitle:   "ExpandableList简单使用实现QQ好友列表效果"
date:       2017-01-1
author:     "y"
header-mask: 0.3
header-img: "img/android.jpg"
catalog: true
tags:
---


## sample

[expandable-list](https://github.com/7449/AndroidDevelop/blob/develop/UtilsSample/ExpandableList)

## 效果图

![_config.yml]({{ site.baseurl }}/img/expandablelist.gif)


## ExpandableList

> 可扩展的下拉列表，可扩展性在于点击父item可以拉下或收起子列表

Adapter继承自`BaseExpandableListAdapter`,如下图所示，会实现一大堆方法

	public class SimpleExpandableListAdapter extends BaseExpandableListAdapter {
	    
	
	    @Override
	    public int getGroupCount() {
	        return 0;
	    }
	
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return 0;
	    }
	
	    @Override
	    public Object getGroup(int groupPosition) {
	        return null;
	    }
	
	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return null;
	    }
	
	    @Override
	    public long getGroupId(int groupPosition) {
	        return 0;
	    }
	
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return 0;
	    }
	
	    @Override
	    public boolean hasStableIds() {
	        return false;
	    }
	
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        return null;
	    }
	
	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	        return null;
	    }
	
	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return false;
	    }
	}


这个时候肯定会选择封装一个`SimpleBaseAdapter`,封装好了之后可以极大的减少往后的代码量


	public abstract class SimpleBaseExpandableListAdapter<T> extends BaseExpandableListAdapter {
	
	
	    protected List<T> expandableListTitle;
	    private HashMap<T, List<T>> expandableListDetail;
	
	    public SimpleBaseExpandableListAdapter(List<T> expandableListTitle, HashMap<T, List<T>> expandableListDetail) {
	        if (expandableListTitle != null && expandableListDetail != null) {
	            this.expandableListDetail = expandableListDetail;
	            this.expandableListTitle = expandableListTitle;
	        }
	    }
	
	
	    @Override
	    public int getGroupCount() {
	        return expandableListTitle.size();
	    }
	
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return expandableListDetail.get(expandableListTitle.get(groupPosition)).size();
	    }
	
	    @Override
	    public Object getGroup(int groupPosition) {
	        return this.expandableListTitle.get(groupPosition);
	    }
	
	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).get(childPosition);
	    }
	
	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }
	
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	
	    @Override
	    public boolean hasStableIds() {
	        return false;
	    }
	
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        return getGroup(groupPosition, isExpanded, convertView, parent);
	    }
	
	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	        return getChild(groupPosition, childPosition, isLastChild, convertView, parent);
	    }
	
	
	    abstract View getGroup(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);
	
	    abstract View getChild(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);
	
	
	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	}


继承自封装好的`Adapter`，可以看到我们只要关心组view和子view就行了，由于这里只是简单的测试，并没有用ViewHolder去实现复用缓存

	public class SimpleAdapter extends SimpleBaseExpandableListAdapter<String> {
	
	
	    public SimpleAdapter(List<String> expandableListTitle, HashMap<String, List<String>> expandableListDetail) {
	        super(expandableListTitle, expandableListDetail);
	    }
	
	    @Override
	    View getGroup(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        String data = expandableListTitle.get(groupPosition);
	
	
	        convertView = View.inflate(parent.getContext(), R.layout.group_item, null);
	
	        TextView textView = (TextView) convertView.findViewById(R.id.tv_group);
	        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv);
	
	        textView.setText(data);
	
	        if (isExpanded) {
	            imageView.setImageResource(R.drawable.dropdown);
	        } else {
	            imageView.setImageResource(R.drawable.select);
	        }
	
	        return convertView;
	    }
	
	    @Override
	    View getChild(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	        String data = (String) this.getChild(groupPosition, childPosition);
	
	        convertView = View.inflate(parent.getContext(), R.layout.child_item, null);
	
	        TextView tv = (TextView) convertView.findViewById(R.id.tv_child);
	        tv.setText(data);
	        return convertView;
	    }
	}



测试数据实现类：


	public class ExpandableListDataPump {
	    public static LinkedHashMap<String, List<String>> getData() {
	        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();
	        List<String> technology = new ArrayList<>();
	        technology.add("A");
	        technology.add("B");
	        technology.add("C");
	        technology.add("D");
	        technology.add("E");
	        technology.add("F");
	        technology.add("G");
	        technology.add("H");
	        technology.add("I");
	        technology.add("J");
	        List<String> entertainment = new ArrayList<>();
	        entertainment.add("A");
	        entertainment.add("B");
	        entertainment.add("C");
	        entertainment.add("D");
	        entertainment.add("E");
	        entertainment.add("F");
	        entertainment.add("G");
	        entertainment.add("H");
	        entertainment.add("I");
	        entertainment.add("J");
	        List<String> science = new ArrayList<>();
	        science.add("A");
	        science.add("B");
	        science.add("C");
	        science.add("D");
	        science.add("E");
	        science.add("F");
	        science.add("G");
	        science.add("H");
	        science.add("I");
	        science.add("J");
	        expandableListDetail.put("SIMPLE ONE", technology);
	        expandableListDetail.put("SIMPLE TWO", entertainment);
	        expandableListDetail.put("SIMPLE THREE", science);
	        return expandableListDetail;
	    }
	}
