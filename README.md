就是一个...想做个自己无聊写的自定义View的合集...

1. ArcSeekBar 就是一个圆环形的滑块.
    1. 支持自定义属性。 
        ```xml
        <attr name="seek_size" format="dimension" />
        <attr name="bar_size" format="dimension" />
        <attr name="text_size" format="dimension" />
        <attr name="arc_color" format="color" />
        <attr name="circle_color" format="color" />
        <attr name="text_color" format="color" />
        <attr name="bar_color" format="color" />
        ```
2. LuLoader 一个模仿百度贴吧的加载动画，自己支持波浪高度调整（也就是可以当个进度条）
    1. 支持自定义属性
        ```xml
        <attr name="text_size" format="dimension" />
        <attr name="text" format="string" />
        <attr name="color" format="color" />
        ```
3. QQListItem 看名字就知道是个仿QQ消息列表的Item，可以侧滑出操作按钮。在做这个控件的时候遇到一个bug，就是在第43行我设置了一个背景色，如果不设置背景色就无法划出操作按钮，但是mSize已经变大了。
    1. 支持自定义按钮数量和按钮文字内容，文字样式，背景颜色。具体看QQListItemButton类
    
4. LuMusicPlay 就是一个模仿虾米音乐播放时的一个动画
    1. 支持自定义属性
        ```xml
        <attr name="padding_item" format="dimension" />
        <attr name="padding" format="dimension" />
        <attr name="background_color" format="color" />
        <attr name="color" format="color" />
        ```

5. LuTabTitle 是一个对TabLayout的自定义。具体看演示吧。hhh