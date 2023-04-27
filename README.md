# 使用Github Action 来实现自动化测试和输出报告

![example workflow](https://github.com/kexi292/testgitaction/actions/workflows/maven.yml/badge.svg)

是Maven 集成 Jacoco 和 junit，统计测试方法的成功率，获取测试方法覆盖率。

具体yml如下:
```
name: Java CI with Maven

#实现：只有push中更改了main分支中的demo文件中的内容时，才执行测试，对应场景：后端代码在demo中
on:
  push:
    branches:
      - 'main'
    paths:
      - 'demo/**'
#mikepenz/action-junit-report@v3 需要部分weite-all（报错中包含了需要check的write权限，此处偷懒）
permissions: write-all

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 8
      uses: actions/setup-java@v3
      with:
        java-version: '8'
        distribution: 'temurin'
        cache: 'maven'
    #关键是--file demo/pom.xml 设置好目录
    - name: Build with Maven
      run: mvn -B install --file demo/pom.xml
      
    #照搬，此处demo中使用maven-surefire-plugin
    - name: Publish Test Report
      uses: mikepenz/action-junit-report@v3
      if: always()
      with:
          report_paths: '**/build/test-results/test/TEST-*.xml'
    #参考jacoco-report中的例子
    - name: Publish test coverage results
      id: jacoco_reporter
      uses: PavanMudigonda/jacoco-reporter@v4.8
      with:
          coverage_results_path: 'demo/target/site/jacoco/jacoco.xml'
          coverage_report_title: 'Test coverage results'
          coverage_report_name: 'Test coverage results'
          github_token: ${{ secrets.GITHUB_TOKEN }}
    
```

帮助很大的参考资料：
      
如需要了解Junit4、5配合maven-surefire-plugin生成Junit报告的最佳实践，可以参考如下资料：
      
JUnit Setup Maven - JUnit 4 and JUnit 5[https://www.digitalocean.com/community/tutorials/junit-setup-maven]

Github 官方文档
