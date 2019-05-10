---
layout:     post
title:      "可控制是否滑动并监控滑动方向的ViewPager"
subtitle:   "ViewPager"
date:       2018-11-3
tags:
    - android
---

    public class CustomViewPager extends ViewPager {
    
        private boolean noScroll = true;
        private float downX;
        private float downY;
        private OnOrientationListener onOrientationListener;
    
        public CustomViewPager(@NonNull Context context) {
            super(context);
        }
    
        public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }
    
        public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
            this.onOrientationListener = onOrientationListener;
        }
    
        public void setNoScroll(boolean noScroll) {
            this.noScroll = noScroll;
        }
    
        @Override
        public void scrollTo(int x, int y) {
            super.scrollTo(x, y);
        }
    
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return !noScroll && super.onTouchEvent(event);
        }
    
        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = x;
                    downY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    float dx = x - downX;
                    float dy = y - downY;
                    if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                        int orientation = getOrientation(dx, dy);
                        switch (orientation) {
                            case OnOrientationListener.RIGHT:
                                if (onOrientationListener != null) {
                                    onOrientationListener.onOrientationRight();
                                }
                                break;
                            case OnOrientationListener.LEFT:
                                if (onOrientationListener != null) {
                                    onOrientationListener.onOrientationLeft();
                                }
                                break;
                            case OnOrientationListener.TOP:
                                if (onOrientationListener != null) {
                                    onOrientationListener.onOrientationTop();
                                }
                                break;
                            case OnOrientationListener.BOTTOM:
                                if (onOrientationListener != null) {
                                    onOrientationListener.onOrientationBottom();
                                }
                                break;
                        }
                    }
                    break;
            }
            return !noScroll && super.onInterceptTouchEvent(event);
        }
    
        private int getOrientation(float dx, float dy) {
            if (Math.abs(dx) > Math.abs(dy)) {
                return dx > 0 ? OnOrientationListener.RIGHT : OnOrientationListener.LEFT;
            } else {
                return dy > 0 ? OnOrientationListener.BOTTOM : OnOrientationListener.TOP;
            }
        }
    
        public interface OnOrientationListener {
            int TOP = 0;
            int BOTTOM = 1;
            int RIGHT = 2;
            int LEFT = 3;
    
            void onOrientationTop();
    
            void onOrientationBottom();
    
            void onOrientationRight();
    
            void onOrientationLeft();
        }
    }
