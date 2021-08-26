# KittSideBar
侧边栏（通讯录）


- xml属性

```
<!--是否显示字母提示-->
<attr name="bar_show_letter_notice" format="enum">
    <!--永远显示-->
    <enum name="FOREVER" value="0x00000000" />
    <!--包含才显示-->
    <enum name="CONTAIN" value="0x00000004" />
    <!--从不显示-->
    <enum name="NEVER" value="0x00000008" />
</attr>


<!--字体大小-->
<attr name="bar_side_letter_textSize" format="dimension" />
<attr name="bar_notice_letter_textSize" format="dimension" />
<!--字体颜色-->
<attr name="bar_side_letter_color" format="color" />
<attr name="bar_side_letter_select_color" format="color" />
<attr name="bar_notice_letter_color" format="color" />

<!--提示框大小-->
<attr name="bar_notice_width" format="dimension" />
<attr name="bar_notice_height" format="dimension" />
<!--提示框背景-->
<attr name="bar_notice_background" format="reference|color" />
```


- setOnTouchSideListener() 触摸SideBar的监听
返回触摸的当前字符(包含大写、小写)，根据后台实际数据大小写进行对应使用

- setChooseLetter()  设置选中的字母

- setContainLetters()  设置数据源所包含char类型的字符，以及当前顶部的item字符
- setContainLetters2()  设置数据源所包含String类型的字符，以及当前顶部的item字符

- setNoticeLetterTextSize()  设置提示文字的大小，默认字体单位为px

- setNoticeColor()  设置提示文字的颜色

- getTargetLetter()  获得当前SideBar目标的字母