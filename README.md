InfiniteCycleViewPager
======================

Infinite cycle ViewPager with two-way orientation and interactive effect.

[![DevLight](https://lh4.googleusercontent.com/-9btnRFp_eVo/V5cfwZsBpMI/AAAAAAAAC4E/s4NGoezKhpAVdVofAoez1QWpzK5Na8_cQCL0B/w147-h20-no/devlight-badge.png)](http://devlight.com.ua)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-NavigationTabBar-yellow.svg?style=flat)](http://android-arsenal.com/details/1/3382)
[![Android](https://img.shields.io/badge/platform-android-brightgreen.svg?style=flat&label=Platform)](https://github.com/DevLight-Mobile-Agency)
[![Download](https://api.bintray.com/packages/gigamole/maven/navigationtabbar/images/download.svg) ](https://bintray.com/gigamole/maven/navigationtabbar/_latestVersion)
[![Crates.io](https://img.shields.io/crates/l/rustc-serialize.svg?maxAge=2592000&label=License)](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/blob/master/LICENSE.txt)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fa85dd3c9aa84d879b3e76be8462145e)](https://www.codacy.com/app/gigamole53/InfiniteCycleViewPager?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DevLight-Mobile-Agency/InfiniteCycleViewPager&amp;utm_campaign=Badge_Grade)

|   	|   	|   	|   	|   	|   	|
|:-:	|:-:	|:-:	|:-:	|:-:	|:-:	|
![](https://lh6.googleusercontent.com/-Bf7uxUiRvfk/VvpVlkZzsVI/AAAAAAAACPA/Ysg9uuBpaL8UhsXpYPlyNJK6IJssdkMvg/w325-h552-no/hntb.gif)|![](https://lh4.googleusercontent.com/-mF70XCnMpgk/V1NnK34tnhI/AAAAAAAACkY/z0Z15Q_7gg4fMovWiEvo9agJgz7m933cQCL0B/w323-h552-no/btbntb.gif)|![](https://lh5.googleusercontent.com/-LcHHajuKNzw/Vz77El2lHsI/AAAAAAAACiQ/I0CjrMUP6R4ioH9h8nEe37LCqXmb3GJKACL0B/w317-h552-no/ntbsi.gif)|![](https://lh6.googleusercontent.com/-hMvLn-jzY3k/VzcPrGAmr4I/AAAAAAAACc0/US0yokfG23kQJEAPxFoPp-8lOUNRSPV9QCL0B/w321-h552-no/cltntb.gif)|![](https://lh4.googleusercontent.com/-k4Ac7-c2m8E/VvpVlk3ZmLI/AAAAAAAACPA/21ISoAYGZzUlvGPmIauXwfYZOKdCYIRGg/w323-h552-no/vntb.gif)|![](https://lh5.googleusercontent.com/-hmELfZQvexU/VvpVlooaPvI/AAAAAAAACPA/5HA5ic7dASwBUYqpqcfxAmfLzPPDXejqQ/w322-h552-no/ntbs.gif)

U can check the sample app [here](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/tree/master/app).

Download
------------

You can download a `.aar` from GitHub's [releases page](https://github.com/DevLight-Mobile-Agency/InfiniteCycleViewPager/releases).

Or use Gradle jCenter:

```groovy
dependencies {
    repositories {
        mavenCentral()
        maven {
            url  'http://dl.bintray.com/gigamole/maven/'
        }
    }
    compile 'com.github.devlight:infinitecycleviewpager:+'
}
```

Or Gradle Maven Central:

```groovy
compile 'com.github.devlight:infinitecycleviewpager:1.0.0'
```

Or Maven:

```groovy
<dependency>
    <groupId>com.github.devlight</groupId>
    <artifactId>infinitecycleviewpager</artifactId>
    <version>1.0.0</version>
    <type>aar</type>
</dependency>
```

Android SDK Version
=========

`InfiniteCycleViewPager` requires a minimum SDK version of 11. 

Sample
========

<b>Parameters</b>

For `NTB` you can set such parameters as:
 
 - models:
    
     allows you to set `NTB` models, where you set icon and color. Can be set up only via code.
    
 - behavior:
     
     allows you to set bottom translation behavior.
    
 - view pager:
     
     allows you to connect `NTB` with `ViewPager`. If you want your can also set `OnPageChangeListener`.
         
 - background color:
 
    allows you to set background to `NTB` which automatically set with offset relative to badge gravity and corners radius.

 - model selected icon:
    
     allows you to set selected icon when current model is active.

 - model title:
    
     allows you to enable title in you model.
    
 - model badge:
     
     allows you to enable badge in you model.
     
 - use custom typeface on badge:
     
     allows you to handle set of custom typeface in your badge.
    
 - title mode:
   
     allows you to handle mode of the model title show. Can show all or only active.
     
 - title size:
    
     allows you to set titles size. 
     
 - scale mode:
    
     allows you to handle mode of the model icon and title scale.
     
 - tint mode:
     
     allows you to enable or disable icon tinting.
      
 - badge size:
     
     allows you to set badges size.
     
 - badge position:
 
     allows you to set the badge position in you model. Can be: left(25%), center(50%) and right(75%).

 - badge gravity:
  
     allows you to set the badge gravity in NTB. Can be top or bottom.
     
 - badge colors:
   
      allows you to set the badge bg and title colors.
    
 - typeface:
 
     allows you to set custom typeface to your title.
 
 - corners radius:
    
     allows you to set corners radius of pointer.

 - icon size fraction:
    
     allows you to set icon size fraction relative to smaller model side.

 - animation duration:
  
     allows you to set animation duration.
      
 - inactive color:

     allows you to set inactive icon color.
     
 - active color:
  
     allows you to set active icon color.
     
 - tab bar listener:
  
     allows you to set listener which triggering on start or on end when you set model index.
        
 - preview colors:
  
     allows you to set preview colors, which generate count of models equals to count of colors.

<b>Tips</b>

Creation of models occurs through `Builder` pattern.

`ModelBuilder` requires two fields: icon and color. Title, badge title and selected icon is optional.

You can set selected icon. Resize and scale of selected icon equals to original icon.

Orientation automatically detected according to `View` size.

By default badge bg color is the active model color and badge title color is the model bg color. To reset colors just set `AUTO_COLOR` value to badge bg and title color.

By default badge sizes and title sizes is auto fit. To reset calculation just set `AUTO_SIZE` value to badge size and title size.

By default icon size fraction is `0.5F` (half of smaller side of `NTB` model). To reset scale fraction of icon to automatic just put in method `AUTO_SCALE` value.

If your set `ViewPager` and enable swipe you can action down on active pointer and do like drag.

<b>Init</b>

Check out in code init:

```java
final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
models.add(
        new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_first),
                Color.parseColor(colors[0])
        ).title("Heart")
                .badgeTitle("NTB")
                .build()
);
models.add(
        new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_second),
                Color.parseColor(colors[1])
        ).title("Cup")
                .badgeTitle("with")
                .build()
);
models.add(
        new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_third),
                Color.parseColor(colors[2])
        ).title("Diploma")
                .badgeTitle("state")
                .build()
);
models.add(
        new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_fourth),
                Color.parseColor(colors[3])
        ).title("Flag")
                .badgeTitle("icon")
                .build()
);
models.add(
        new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_fifth),
                Color.parseColor(colors[4])
        ).title("Medal")
                .badgeTitle("777")
                .build()
);
navigationTabBar.setModels(models);
navigationTabBar.setViewPager(viewPager, 2);

navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
navigationTabBar.setTypeface("fonts/custom_font.ttf");
navigationTabBar.setIsBadged(true);
navigationTabBar.setIsTitled(true);
navigationTabBar.setIsTinted(true);
navigationTabBar.setIsBadgeUseTypeface(true);
navigationTabBar.setBadgeBgColor(Color.RED);
navigationTabBar.setBadgeTitleColor(Color.WHITE);
navigationTabBar.setIsSwiped(true);
navigationTabBar.setBgColor(Color.BLACK);
navigationTabBar.setBadgeSize(10);
navigationTabBar.setTitleSize(10);
navigationTabBar.setIconSizeFraction(0.5);
```

If your models is in badge mode you can set title, hide, show, toggle and update badge title like this:
```java
model.setTitle("Here some title to model");
model.hideBadge();
model.showBadge();
model.toggleBadge();
model.updateBadgeTitle("Here some title like NEW or some integer value");
```
          
To enable behavior translation inside `CoordinatorLayout` when at bottom of screen:
```java
navigationTabBar.setBehaviorEnabled(true);
```

To deselect active index and reset pointer:
```java
navigationTabBar.deselect();
```

Other methods check out in sample.

And XML init:

```xml
<com.gigamole.navigationtabbar.ntb.NavigationTabBar
   android:id="@+id/ntb"
   android:layout_width="match_parent"
   android:layout_height="50dp"
   app:ntb_animation_duration="400"
   app:ntb_preview_colors="@array/colors"
   app:ntb_corners_radius="10dp"
   app:ntb_active_color="#fff"
   app:ntb_inactive_color="#000"
   app:ntb_badged="true"
   app:ntb_titled="true"
   app:ntb_scaled="true"
   app:ntb_tinted="true"
   app:ntb_title_mode="all"
   app:ntb_badge_position="right"
   app:ntb_badge_gravity="top"
   app:ntb_badge_bg_color="#ffff0000"
   app:ntb_badge_title_color="#ffffffff"
   app:ntb_typeface="fonts/custom_typeface.ttf"
   app:ntb_badge_use_typeface="true"
   app:ntb_swiped="true"
   app:ntb_bg_color="#000"
   app:ntb_icon_size_fraction="0.5"
   app:ntb_badge_size="10sp"
   app:ntb_title_size="10sp"/>
```

Getting Help
======

To report a specific problem or feature request, [open a new issue on Github](https://github.com/DevLight-Mobile-Agency/NavigationTabBar/issues/new).

License
======

Apache 2.0 and MIT. See [LICENSE](https://github.com/DevLight-Mobile-Agency/NavigationTabBar/blob/master/LICENSE.txt) file for details.

Inspiration
===========

|   	|   	|   	|   	|
|:-:	|:-:	|:-:	|:-:	|
![](https://s-media-cache-ak0.pinimg.com/originals/39/ee/33/39ee330f3460bd638284f0576bc95b65.gif)|![](https://s-media-cache-ak0.pinimg.com/564x/f4/0d/a9/f40da9e5b73eb5e0e46681eba38f1347.jpg)|![](https://s-media-cache-ak0.pinimg.com/564x/14/eb/dd/14ebddfc0d92f02be3d61ede48a9da6e.jpg)|![](https://d13yacurqjgara.cloudfront.net/users/25514/screenshots/1367175/attachments/195679/Sleep-Tracker-iPhone-UI-ramotion.png)

Thanks to [Valery Nuzhniy](https://www.pinterest.com/hevbolt/) for NTB badge design.

Author
=======

Made in [DevLight Mobile Agency](https://github.com/DevLight-Mobile-Agency)

Created by [Basil Miller](https://github.com/GIGAMOLE) - [@gigamole](mailto:gigamole53@gmail.com)

Support
=======

If you'd like to support future development and new product features, please make a payments on Gratipay and Beerpay or become a patron on Patreon.

[![Gratipay](https://img.shields.io/gratipay/user/gigamole.svg?maxAge=2592000)](https://gratipay.com/~GIGAMOLE/)
[![Beerpay](https://beerpay.io/DevLight-Mobile-Agency/NavigationTabBar/badge.svg?style=flat)](https://beerpay.io/DevLight-Mobile-Agency/NavigationTabBar)
[![Patreon](https://lh5.googleusercontent.com/-lXI_oKp5724/V58ysdDtxHI/AAAAAAAAC7s/g91W_YT2SM0Q_VaIhDAMmoe-jHPP3ijJwCL0B/w140-h20-no/patreon-badge.png)](https://www.patreon.com/gigamole)

Also, if you use this library in applications that are available on Google Play, please report it to us or author.

Thanks in advance.