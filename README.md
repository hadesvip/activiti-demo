## activiti-demo
> 工作流引擎activiti一个小例子，基于5.22.0
1. 下载官方zip包，打开redeme.html,参考里面的10分钟教程，用户指南，javadocs文档
   https://github-production-release-asset-2e65be.s3.amazonaws.com/5793738/bb012522-a2a3-11e6-89eb-41eb5457c598?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20170707%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20170707T082012Z&X-Amz-Expires=300&X-Amz-Signature=af75c1ea1faa1cb6c0e0f74ba83248dc62311f7e9e8cec9a5f78d54e44c39c36&X-Amz-SignedHeaders=host&actor_id=9640832&response-content-disposition=attachment%3B%20filename%3Dactiviti-5.22.0.zip&response-content-type=application%2Foctet-stream
               
2. activiti-explorer(activiti 流程设计项目)
    * git checkout https://github.com/Activiti/Activiti.git
    * 切换到github线上 5.22.0-release版本 git checkout -b 5.22.0 origin/5.22.0-release
    * 将数据库从h2转为mysql
    * 几个重要的配置文件
      * db.properties 数据库配置
      * engine.properties 默认创建用户和组，还有流程定义和模型。
         