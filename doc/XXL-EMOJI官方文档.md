## 《Emoji表情编解码库XXL-EMOJI》

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.xuxueli/xxl-emoji/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.xuxueli/xxl-emoji/)
[![GitHub release](https://img.shields.io/github/release/xuxueli/xxl-emoji.svg)](https://github.com/xuxueli/xxl-emoji/releases)
[![License](https://img.shields.io/badge/license-GPLv3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0.html)
[![donate](https://img.shields.io/badge/%24-donate-ff69b4.svg?style=flat-square)](http://www.xuxueli.com/page/donate.html)

## 一、简介

### 1.1 概述
XXL-EMOJI 是一个灵活可扩展的Emoji库，可快速实现Emoji表情Unicode的编解码.

### 1.2 特性
- 1、简洁：API直观简洁，一分钟上手；
- 2、易扩展：模块化的结构，可轻松扩展；
- 3、别名自定义：支持为Emoji自定义别名；
- 4、实时性：实时收录最新发布的Emoji；


### 1.3 下载

#### 文档地址

- [中文文档](http://www.xuxueli.com/xxl-emoji/)

#### 源码仓库地址

源码仓库地址 | Release Download
--- | ---
[https://github.com/xuxueli/xxl-emoji](https://github.com/xuxueli/xxl-emoji) | [Download](https://github.com/xuxueli/xxl-emoji/releases)
[https://gitee.com/xuxueli0323/xxl-emoji](https://gitee.com/xuxueli0323/xxl-emoji) | [Download](https://gitee.com/xuxueli0323/xxl-emoji/releases)  


#### 技术交流
- [社区交流](http://www.xuxueli.com/page/community.html)

### 1.4 环境
- JDK：1.7+


## 二、快速入门


### 第一步：引入Maven依赖
```
<dependency>
    <groupId>com.xuxueli</groupId>
    <artifactId>xxl-emoji</artifactId>
    <version>${最新稳定版}</version>
</dependency>
```

### 第二步：定义 "PageVo/页面数据对象"（可选）



## 三、总体设计

### 3.1 功能定位

XXL-EMOJI 是一个灵活可扩展的Emoji库，可快速实现Emoji表情Unicode的编解码.

### 3.2 Emoji编码类型

概念 | 说明
--- | ---
EmojiEncode.ALIASES | 将Emoji表情转换为别名，格式为 ":alias:"；
EmojiEncode.HTML_DECIMAL | 将Emoji表情Unicode数据转换为十进制数据；
EmojiEncode.HTML_HEX_DECIMAL | 将Emoji表情Unicode数据转换为十六进制数据；

### 3.3、Emoji编解码API

API | 说明
--- | ---
public static String encodeUnicode(String input, EmojiTransformer transformer, FitzpatrickAction fitzpatrickAction) | Emoji表情编码方法，支持自定义编码逻辑；
public static String encodeUnicode(String input, EmojiEncode emojiEncode, FitzpatrickAction fitzpatrickAction) | Emoji表情编码方法，支持自定义编码类型；
public static String encodeUnicode(String input, EmojiEncode emojiEncode) | Emoji表情编码方法，支持自定义编码类型；
public static String encodeUnicode(String input) | Emoji表情编码方法，编码类型默认为 "ALIASES" ；
public static String decodeToUnicode(String input) | Emoji表情解码方法，支持针对 "ALIASES、HTML_DECIMAL、HTML_HEX_DECIMAL" 等编码方式解码；
public static String removeEmojis(String input, final Collection<Emoji> emojisToRemove, final Collection<Emoji> emojisToKeep) | 获取待采集URL数量；
public static String removeEmojis(String input, final Collection<Emoji> emojisToRemove, final Collection<Emoji> emojisToKeep) | 获取待采集URL数量；
public static List<String> findEmojis(String input) | 


## 四、版本更新日志
### 版本 V1.0.0，新特性[2018-07-06]
- 1、简洁：API直观简洁，一分钟上手；
- 2、易扩展：模块化的结构，可轻松扩展；
- 3、别名自定义：支持为Emoji自定义别名；
- 4、实时性：实时收录最新发布的Emoji；


### TODO LIST
- 1、Emoji远程编解码服务；

## 五、其他

### 5.1 项目贡献
欢迎参与项目贡献！比如提交PR修复一个bug，或者新建 [Issue](https://github.com/xuxueli/xxl-emoji/issues/) 讨论新特性或者变更。

### 5.2 用户接入登记
更多接入的公司，欢迎在 [登记地址](https://github.com/xuxueli/xxl-emoji/issues/1 ) 登记，登记仅仅为了产品推广。

### 5.3 开源协议和版权
产品开源免费，并且将持续提供免费的社区技术支持。个人或企业内部可自由的接入和使用。

- Licensed under the GNU General Public License (GPL) v3.
- Copyright (c) 2015-present, xuxueli.

---
### 捐赠
无论金额多少都足够表达您这份心意，非常感谢 ：）      [前往捐赠](http://www.xuxueli.com/page/donate.html )