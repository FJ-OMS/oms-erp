[English](./README_EN.md) | 简体中文

<p align="center">
  
[![HitCount](http://hits.dwyl.com/eeveek/oms.svg)](http://hits.dwyl.com/eeveek/oms)
![GitHub All Releases](https://img.shields.io/github/downloads/eeveek/oms/total)
[![GitHub issues](https://img.shields.io/github/issues/eeveek/oms)](https://github.com/eeveek/oms/issues)
![GitHub closed issues](https://img.shields.io/github/issues-closed/eeveek/oms)
[![GitHub forks](https://img.shields.io/github/forks/eeveek/oms)](https://github.com/eeveek/oms/network)
[![GitHub stars](https://img.shields.io/github/stars/eeveek/oms)](https://github.com/eeveek/oms/stargazers)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/eeveek/oms)
[![GitHub license](https://img.shields.io/github/license/eeveek/oms)](https://github.com/eeveek/oms/blob/master/LICENSE)
</p>

# 🔺[开源的新零售业务中台OMS](https://www.k8s9.com)
```
全渠道一盘货订单管理系统OMS,基于业内领先的微服务架构，打造电商通用业务中台，实现快速部署，助力企业加速实现业务一体化与数字化转型。企业可以处理来自所有渠道的订单，降低库存仓位将资金周转率提升至少50%，减少占订单金额2%-10%左右的内部损耗，将开发部署周期减少到几周内，将开发成本控制在百万元左右，能自主可控的让系统跟上业务的变化。
```
## 🔺介绍
```
项目业务中台OMS全渠道一盘货订单管理系统的内核是通过打造通用的中台系统，实现快速部署，并帮助企业后续自主进行开发迭代，来帮助企业实现数字化转型。

提供的中台系统包括：通用业务中台、强大的技术中台、后续开发方案的设计，技术及管理人员的培训等。

其中业务中台通过将订单、库存、财务、商品、营销等业务管理起来，构造适应新零售条件下管理模式的系统，帮助企业实现数字化条件下的转型升级。

技术中台通过微服务架构，流水线自动部署等，实现让水平一般的程序员开发高复杂度的中台系统，降低企业在技术上的投入。

经过方案设计和人员培训后，会为企业培养出一只自主可控有水平的技术团队，方便企业后续自主的开发系统，以跟得上快速变化的市场环境，减少总体的上线时间，降低企业运营成本。
```
__帮助企业和个人解决以下的问题__

```
1、全渠道订单，及线上线下订单难以融合处理的问题。

2、库存无法共享处理，库存仓位过重的问题。

3、财务无法实时统计，单单处理，资金周转率过低的问题。

4、商品、价格、营销、权限等数据各自分散，无法综合管理，造成大量内部损耗的问题。

5、开发周期长，人员要求高，成本高，整体迭代受制于人的问题。

6、市场变化太快，系统无法及时跟得上业务的问题。
```
## 🔺软件展示和业务架构

![img](https://github.com/FJ-OMS/oms-erp/blob/main/%E4%B8%9A%E5%8A%A1.png)
![img](https://github.com/FJ-OMS/oms-erp/blob/main/%E9%85%8D%E7%BD%AE.png)

__业务架构__
 
    通过汇总源于京东、淘宝、天猫、抖音和拼多多等渠道订单，并在业务中台内部转化成内部订单，整合订单中心和采购中心与库存中心、财务中心的业务流程，使企业的订单、库存和财务三方数据形成铁三角。

![img](https://github.com/eeveek/oms/blob/main/%E4%B8%9A%E5%8A%A1%E6%9E%B6%E6%9E%84.png)

__技术架构__ 

    基于spring cloud 微服务框架 支持多租户体系 微服务工作流Zeebe和activiti 报表自定义等功能，除了上述的应用功能，还将Redis、MyBatis、分布式事务saga、分布式锁和检索等一系列服务组件化，便于二次开发。


![img](https://github.com/FJ-OMS/oms-erp/blob/main/%E6%8A%80%E6%9C%AF%E6%9E%B6%E6%9E%84.png)

## 🔺特技

__云原生__
```
基于分布部署和统一运管，以分布式云，以容器、微 服务、DevOps等技术为基础建立技术产品体系
```
__开发__

```
包括集成GitLab的代码统一管理能力、支持多种类型的制品库管理能力、集成CI/CD的自动化流水线、内建的代码扫描与质量分析，全流程跟进开发过程
```
__部署__
```
包括集群管理、环境管理、资源管理，支持集群与环境的统一管理和集中分配，支持自动化部署流水线与一键式的手工部署，高效灵活地支撑产品运维
```
__测试__
```
包括测试用例管理、测试计划管理、测试执行管理、缺陷管理、 测试报告管理，支持手工测试并提供API测试、性能测试、流量 回归测试、UI测试等自动化测试能力，测试任务还可嵌入开发流 水线，持续测试有效保障产品质量
```

##使用说明
1.前端配置：
项目使用了 element-ui、vue。确保这些前端库已正确引入和配置。
命令行输入npm run dev
2.后端配置：
项目使用了 MySQL 和 Redis 数据库。您需要在 application.yml 文件中配置数据库连接信息。
MySQL 配置：
数据库连接URL：jdbc:mysql://<local_ip>:3306/skyer_after_sales?useUnicode=true&characterEncoding=utf-8&useSSL=false
用户名：<local_username>
密码：<local_password>
Redis配置：
redis.host: <local_redis_ip>
3.登录网站
服务器端口号：8080，启动成功后访问http://127.0.0.1:8080，用户名和密码在数据库的用户信息表中。
4.后端框架：
后端框架是 Spring Boot 框架。
5.导入 SQL 文件：
项目需要导入 oms-erp-main\skyer-order\sql，oms-erp-main\skyer-tags\sql中的sql文件，以及oms-erp-main\skyer-channel\src\main\resources\skyer_channel.sql 文件到数据库中。您可以使用数据库管理工具或命令行工具执行该 SQL 文件。
6.启动项目：
项目的启动方式是直接运行 Application.java 文件。确保您的开发环境配置正确，项目的依赖项已经安装。

## 🔺联系方式和试用

```
演示地址：https://www.k8s9.com/ 点击导航栏上的免费试用 用户名：demo 密码:abcd1234  
微信:blowbing
```
![img](https://github.com/eeveek/oms/blob/main/front-end/we.png)  ![img](https://github.com/eeveek/oms/blob/main/front-end/gzh.png)

## 🔺安装教程

## 🔺使用说明

## 🔺参与贡献
