# sslBaseLibrary
项目底层库；工具类库；简单实用Mvp；图片库抽离


使用说明
module说明：
commonUtilsLib：工具类库；包括工具类、基于okhttp3的封装、基于glide的图片库封装
commonCoreLib：框架管理库；包括基于接口的组件化封装、简单MVP封装
moduleBase：基础项目module封装，针对项目提供统一的TitleBar、BaseActivity等，该库需要针对自己项目调整



导入说明：
一、目前lib中使用的aar的版本写在它的build.gradle文件中，修改需要手动修改

二、在项目的settings.gradle添加module
include ':commonUtilsLib'
project(':commonUtilsLib').projectDir = new File('../sslBaseLibrary/commonUtilsLib')

include ':commonCoreLib'
project(':commonCoreLib').projectDir = new File('../sslBaseLibrary/commonCoreLib')

include ':moduleBase'
project(':moduleBase').projectDir = new File('../sslBaseLibrary/moduleBase')

三、引用moduleBase库配置greenDao
在根build.gradle配置
  classpath "org.greenrobot:greendao-gradle-plugin:3.2.2"


四、远程子项目依赖
git submodule add https://github.com/lonelyssl/sslBaseLibrary.git
修改settings.gradle
include ':commonUtilsLib'
project(':commonUtilsLib').projectDir = new File('sslBaseLibrary/commonUtilsLib')

include ':commonCoreLib'
project(':commonCoreLib').projectDir = new File('sslBaseLibrary/commonCoreLib')

include ':moduleBase'
project(':moduleBase').projectDir = new File('sslBaseLibrary/moduleBase')
