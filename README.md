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

|-|-|-|
![](https://lh5.googleusercontent.com/-DpQ9a_kxOhg/V7sDRu6BFlI/AAAAAAAADEM/NLIYoehpbq41sx6qTd5JZ3rrkE6xHQ4RQCL0B/w241-h381-no/hicvp.gif)|![](https://lh6.googleusercontent.com/-eojSc_l1OLs/V7sDRvJYKWI/AAAAAAAADEM/ZrWnluueFjsKw9vLrrcLaYS1P-DMgcoEQCL0B/w241-h382-no/vicvp.gif)|![](https://lh3.googleusercontent.com/-qRRmP-bR-fg/V7sDRjviAYI/AAAAAAAADEM/jMhU4PR4ZqAsScoMz-k8ECCAlGgrY2phQCL0B/w241-h381-no/twicvp.gif)

U can check the sample app [here](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/tree/master/app).

Download
------------

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
=========

`InfiniteCycleViewPager` requires a minimum SDK version of 11. 

Sample
========

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
======

To report a specific problem or feature request, [open a new issue on Github](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/issues/new).

License
======

Apache 2.0 and MIT. See [LICENSE](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/blob/master/LICENSE.txt) file for details.

Inspiration
===========

|   	|   	|
|:-:	|:-:	|
![](https://lh5.googleusercontent.com/-pUxe24V4ePI/V7sDRnBIN9I/AAAAAAAADEM/0U3G2lB_HmAbNFO6qOLO6yQ02f1jTjAFgCL0B/w520-h390-no/icvp.gif)|![](http://dbwgapw6amg93.cloudfront.net/wp-content/uploads/2016/05/personal-festival-app-beautiful-mobile-ui.jpg?097e9c)

Author
=======

Made in [DevLight Mobile Agency](https://github.com/DevLight-Mobile-Agency)

Created by [Basil Miller](https://github.com/GIGAMOLE) - [@gigamole](mailto:gigamole53@gmail.com)

Support
=======

If you'd like to support future development and new product features, please make a payments on Gratipay and Beerpay or become a patron on Patreon.

[![Gratipay](https://img.shields.io/gratipay/user/gigamole.svg?maxAge=2592000)](https://gratipay.com/~GIGAMOLE/)
[![Beerpay](https://beerpay.io/DevLight-Mobile-Agency/InfiniteCycleViewPager/badge.svg?style=flat)](https://beerpay.io/DevLight-Mobile-Agency/InfiniteCycleViewPager)
[![Patreon](https://lh5.googleusercontent.com/-lXI_oKp5724/V58ysdDtxHI/AAAAAAAAC7s/g91W_YT2SM0Q_VaIhDAMmoe-jHPP3ijJwCL0B/w140-h20-no/patreon-badge.png)](https://www.patreon.com/gigamole)

Also, if you use this library in applications that are available on Google Play, please report it to us or author.

Thanks in advance.
