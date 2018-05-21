<br/>
<p align="center">
  <a href="http://devlight.io">
      <img src ="https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScLVBKck51Z1Rzb0E" alt="Devlight"/>
  </a>
</p>
<br/>

InfiniteCycleViewPager
======================

Infinite cycle ViewPager with two-way orientation and interactive effect.

[![Android Arsenal](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScbldGMXh5VjBjZEE)](http://android-arsenal.com/details/1/4207)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![Android](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wSccEZaclNGN0R5OWc)](https://github.com/DevLight-Mobile-Agency)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![Download](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScTkFkZk1UdEN3MHM)](https://bintray.com/gigamole/maven/infinitecycleviewpager/_latestVersion)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![License](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScU0tmeFpGMHVWNWs)](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/blob/master/LICENSE.txt)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![Codacy](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScSHhmckZyeGJDcXc)](https://www.codacy.com/app/gigamole53/InfiniteCycleViewPager?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DevLight-Mobile-Agency/InfiniteCycleViewPager&amp;utm_campaign=Badge_Grade)

<br/>

<p align="center">
    <img src="https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScS0hwRTZUaG16Znc"/>
    &nbsp;
    <img src="https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScNmRmWXAzRmRwNHM"/>
</p>

U can check the sample app [here](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/tree/master/app).

Warn
====
```
This library is not more supported. 
If you want to add new feature or fix a bug, grab source code and do it. 
If you think your fix or feature would be useful to other developers, 
I can add link of your repository to this README file. 
Thank you for using our libraries.
```

Download
========

You can download a `.aar` from GitHub's [releases page](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/releases).

Or use Gradle:

```groovy
compile 'com.github.devlight:infinitecycleviewpager:1.0.2'
```

Or Maven:

```groovy
<dependency>
  <groupId>com.github.devlight</groupId>
  <artifactId>infinitecycleviewpager</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
```

Or Ivy:

```groovy
<dependency org='com.github.devlight' name='infinitecycleviewpager' rev='1.0.2'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

Android SDK Version
===================

`InfiniteCycleViewPager` requires a minimum SDK version of 11. 

Sample
======

<b>Parameters</b>

For `InfiniteCycleViewPager` you can set such parameters as:
 
 - min scale:  
     allows you to set the minimum scale of left and right bottom pages.
    
 - max scale:  
     allows you to set the maximum scale of center top page.
    
 - min scale offset:  
     allows you to set offset from edge to minimum scaled pages.
         
 - center scale offset:  
    allows you to set offset from center when two pages appears.

 - model selected icon:  
     allows you to set selected icon when current model is active.

 - medium scaled:  
     allows you to set is scaling would be min -> max or min -> medium -> max.
    
 - scroll duration:  
     allows you to set snap scrolling duration.
     
 - scroll interpolator:  
     allows you to set snap scrolling interpolator.
    
 - page transform listener:  
     allows you to set page transform listener.
     
 - auto scroll:  
     allows you to set auto scroll in positive and negative directions.
    
<b>Tips</b>

Two-way widget need a lot of memory.  
Infinite scroll available when item count more then 2.  
You can set vertical or horizontal infinite cycle ViewPager.

<b>Init</b>

Check out in code init:

```java
//      final VerticalInfiniteCycleViewPager infiniteCycleViewPager =
//                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
        final HorizontalInfiniteCycleViewPager infiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        infiniteCycleViewPager.setAdapter(...);
        infiniteCycleViewPager.setScrollDuration(500);
        infiniteCycleViewPager.setInterpolator(...);
        infiniteCycleViewPager.setMediumScaled(true);
        infiniteCycleViewPager.setMaxPageScale(0.8F);
        infiniteCycleViewPager.setMinPageScale(0.5F);
        infiniteCycleViewPager.setCenterPageScaleOffset(30.0F);
        infiniteCycleViewPager.setMinPageScaleOffset(5.0F);
        infiniteCycleViewPager.setOnInfiniteCyclePageTransformListener(...);
```

If you want to get item position just call this method:  
```java
infiniteCycleViewPager.getRealItem();
```
          
To update your ViewPager after some adapter update or else, you can call this method:  
```java
infiniteCycleViewPager.notifyDataSetChanged();
```

If you want to start auto scroll or stop call this methods:  
```java
// true - positive
// false - negative
infiniteCycleViewPager.startAutoScroll(...);
infiniteCycleViewPager.stopAutoScroll();
```

Other methods check out in sample.

And XML init:

```xml

<!--<com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager-->
<com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:icvp_interpolator="..."
    app:icvp_center_page_scale_offset="30dp"
    app:icvp_max_page_scale="0.8"
    app:icvp_medium_scaled="true"
    app:icvp_min_page_scale="0.5"
    app:icvp_min_page_scale_offset="5dp"
    app:icvp_scroll_duration="500"/>
```

Getting Help
============

To report a specific problem or feature request, [open a new issue on Github](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/issues/new).

Xamarin
=======

Thanks to [Martijn van Dijk](https://github.com/martijn00) for developing Xamarin bindings library for [InfiniteCycleViewPager](https://github.com/martijn00/InfiniteCycleViewPagerXamarin).  
Plugin is available on [Nuget](https://www.nuget.org/packages/Xam.Plugins.Android.InfiniteCycleViewPager/).

Credits
=======

|[Yehor Kosinov](https://dribbble.com/Kosinov)|[Prakhar Neel Sharma](https://dribbble.com/prakhar)|
|:-------------------------------------------:|:-------------------------------------------------:|
[![](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScVEZRbk5LaFVzUzQ)](https://dribbble.com/shots/2832006-Cinema-App)|[![](https://d13yacurqjgara.cloudfront.net/users/452635/screenshots/2383427/festival.png)](https://dribbble.com/shots/2383427-Personal-Festival-App-Finally-Done)

Author
======

Created by [Basil Miller](https://github.com/GIGAMOLE) - [@gigamole](mailto:gigamole53@gmail.com)

Company
=======

[![Facebook](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScaGhGVFNKU0VxMnc)](https://www.facebook.com/devlightagency)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![Twitter](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wScZ1ExQWh5cHF5cVE)](https://twitter.com/DevLightIO)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[![LinkedIn](https://drive.google.com/uc?export=download&id=0BxPO_UeS7wSccGZINzEycE1nVFE)](https://www.linkedin.com/company/devlight)

[Here](https://github.com/DevLight-Mobile-Agency) you can see open source work developed by Devlight LLC.  
This and another works is an exclusive property of Devlight LLC. 

If you want to use this library in applications which will be available on Google Play, please report us about it or author of the library.

Whether you're searching for a new partner or trusted team for creating your new great product we are always ready to start work with you. 

You can contact us via info@devlight.io or opensource@devlight.io.  
Thanks in advance.

Devlight LLC, 2016  
[devlight.io](http://devlight.io) 

