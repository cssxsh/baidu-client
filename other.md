# 设备类型
| TYPE |    NAME    |
|:----:|:----------:|
|  0   | Windows网页版 |
|  7   | Windows客户端 |

# API错误码

| CODE  | MESSAGE                                                                 | COMMENT                              |
|:-----:|:------------------------------------------------------------------------|:-------------------------------------|
|  -3   |                                                                         | 转存文件不存在                              |
|  -6   |                                                                         | 身份验证失败/access_token失效                |
|  -7   |                                                                         | 文件或目录无权访问/达到高级会员转存上限                 |
|  -8   |                                                                         | 文件或目录已存在                             |
|  -9   |                                                                         | 文件或目录不存在/pwd错误                       |
|  -10  |                                                                         | 云端容量已满                               |
|  -10  |                                                                         | 外链已过期或用户取消分享                         |
|  -21  |                                                                         | 参数错误                                 |
|  -33  |                                                                         | 达到转存文件数目上限                           |
|   0   | no error                                                                | 没有错误                                 |
|   1   | Unknown error                                                           | 未知错误                                 |
|   2   | Service temporarily unavailable                                         | 服务暂时不可用/参数错误,或者判断是否有 referer         |
|   3   | Unsupported openapi method                                              | 不支持此接口                               |
|   4   | Open api request limit reached                                          | 该APP访问该接口的QPS达到上限                    |
|   5   | Unauthorized client IP address                                          | 访问的客户端IP不在白名单内/分享文件夹等禁止文件            |
|   6   | No permission to access data                                            | 该APP没有访问该接口的权限/bduss失效，登录态异常         |
|  10   |                                                                         | 创建文件失败                               |
|  11   |                                                                         | 用户不存在(uid不存在)                        |
|  12   |                                                                         | 批量操作失败                               |
|  17   | Open api daily request limit reached                                    | 该APP访问该接口超过每天的访问限额                   |
|  18   | Open api qps request limit reached                                      | 该APP访问该接口超过QPS限额                     |
|  19   | Open api total request limit reached                                    | 该APP访问该接口超过总量限额                      |
|  100  | Invalid parameter                                                       | 没有获取到token参数                         |
|  101  | Invalid API key                                                         | 无效API Key                            |
|  102  | Session key invalid or no longer valid                                  | 会话密钥无效                               |
|  103  | Invalid/Used xxx parameter                                              | xxx参数无效/已被使用                         |
|  104  | Incorrect signature                                                     | 签名错误                                 |
|  105  | Too many parameters                                                     | 参数过多/链接地址错误/非会员用户达到转存文件数目上限          |
|  106  | Unsupported signature method                                            | 不支持此签名方式                             |
|  107  | Invalid/Used timestamp parameter                                        | 时间戳无效                                |
|  108  | Invalid user id                                                         | 用户ID无效                               |
|  109  | Invalid user info field                                                 | 用户信息字段无效                             |
|  110  | Access token invalid or no longer valid                                 | token不合法/有其他转存任务在进行                  |
|  111  | Access token expired                                                    | token已过期/有其他异步任务正在执行                 |
|  112  | Session key expired                                                     | 会话密钥已过期                              |
|  114  | Invalid Ip                                                              | 无效IP                                 |
|  120  | Invalid Ip                                                              | 非会员用户达到转存文件数目上限                      |
|  130  | Invalid Ip                                                              | 达到高级会员转存上限                           |
|  133  | No permission to access user mobile                                     | 播放广告                                 |
|  140  |                                                                         | 外链不存在                                |
|  213  | No permission to access user mobile                                     | 没有权限获取用户手机号                          |
| 31001 | db query error                                                          | 数据库查询错误                              |
| 31002 | db connect error                                                        | 数据库连接错误                              |
| 31003 | db result set is empty                                                  | 数据库返回空结果                             |
| 31021 | network error                                                           | 网络错误                                 |
| 31022 | can not access server                                                   | 暂时无法连接服务器                            |
| 31023 | param error                                                             | 输入参数错误                               |
| 31024 | app id is empty                                                         | app id为空 /没有申请上传权限                   |
| 31025 | bcs error                                                               | 后端存储错误                               |
| 31034 |                                                                         | 命中接口频控                               |
| 31041 | bduss is invalid                                                        | 用户的cookie不是合法的百度cookie               |
| 31042 | user is not login                                                       | 用户未登陆                                |
| 31043 | user is not active                                                      | 用户未激活                                |
| 31044 | user is not authorized                                                  | 用户未授权                                |
| 31045 | user not exists                                                         | 用户不存在/access_token验证未通过              |
| 31046 | user already exists                                                     | 用户已经存在                               |
| 31061 | file already exists                                                     | 文件已经存在                               |
| 31062 | file name is invalid                                                    | 文件名非法                                |
| 31063 | file parent path does not exist                                         | 文件父目录不存在                             |
| 31064 | file is not authorized                                                  | 无权访问此文件                              |
| 31065 | directory is full                                                       | 目录已满                                 |
| 31066 | file does not exist                                                     | 文件不存在                                |
| 31067 | file deal failed                                                        | 文件处理出错                               |
| 31068 | file create failed                                                      | 文件创建失败                               |
| 31069 | file copy failed                                                        | 文件拷贝失败                               |
| 31070 | file delete failed                                                      | 文件删除失败                               |
| 31071 | get file meta failed                                                    | 不能读取文件元信息                            |
| 31072 | file move failed                                                        | 文件移动失败                               |
| 31073 | file rename failed                                                      | 文件重命名失败                              |
| 31081 | superfile create failed                                                 | superfile创建失败                        |
| 31082 | superfile block list is empty                                           | superfile 块列表为空                      |
| 31083 | superfile update failed                                                 | superfile 更新失败                       |
| 31101 | tag internal error                                                      | tag系统内部错误                            |
| 31102 | tag param error                                                         | tag参数错误                              |
| 31103 | tag database error                                                      | tag系统错误                              |
| 31110 | access denied to set quota                                              | 未授权设置此目录配额                           |
| 31111 | quota only sopport 2 level directories                                  | 配额管理只支持两级目录                          |
| 31112 | exceed quota                                                            | 超出配额                                 |
| 31113 | the quota is bigger than one of its parent directorys                   | 配额不能超出目录祖先的配额                        |
| 31114 | the quota is smaller than one of its sub directorys                     | 配额不能比子目录配额小                          |
| 31141 | thumbnail failed, internal error                                        | 请求缩略图服务失败                            |
| 31190 |                                                                         | 文件不存在                                |
| 31201 | signature error                                                         | 签名错误                                 |
| 31203 | acl put error                                                           | 设置acl失败                              |
| 31204 | acl query error                                                         | 请求acl验证失败                            |
| 31205 | acl get error                                                           | 获取acl失败                              |
| 31079 | File md5 not found, you should use upload API to upload the whole file. | 未找到文件MD5，请使用上传API上传整个文件。             |
| 31202 | object not exists                                                       | 文件不存在                                |
| 31206 | acl get error                                                           | acl不存在                               |
| 31207 | bucket already exists                                                   | bucket已存在                            |
| 31208 | bad request                                                             | 用户请求错误                               |
| 31209 | baidubs internal error                                                  | 服务器错误                                |
| 31210 | not implement                                                           | 服务器不支持                               |
| 31211 | access denied                                                           | 禁止访问                                 |
| 31212 | service unavailable                                                     | 服务不可用                                |
| 31213 | service unavailable                                                     | 重试出错                                 |
| 31214 | put object data error                                                   | 上传文件data失败                           |
| 31215 | put object meta error                                                   | 上传文件meta失败                           |
| 31216 | get object data error                                                   | 下载文件data失败                           |
| 31217 | get object meta error                                                   | 下载文件meta失败                           |
| 31218 | storage exceed limit                                                    | 容量超出限额                               |
| 31219 | request exceed limit                                                    | 请求数超出限额                              |
| 31220 | transfer exceed limit                                                   | 流量超出限额                               |
| 31298 | the value of KEY[VALUE] in pcs response headers is invalid              | 服务器返回值KEY非法                          |
| 31299 | no KEY in pcs response headers                                          | 服务器返回值KEY不存在 /第一个分片的大小小于4MB          |
| 31301 |                                                                         | 非音视频文件                               |
| 31304 |                                                                         | 当前视频格式不支持播放                          |
| 31324 |                                                                         | 无访问权限                                |
| 31326 |                                                                         | 命中防盗链，需检查User-Agent请求头是否正常。          |
| 31338 |                                                                         | 当前视频码率太高暂不支持流畅播放                     |
| 31341 |                                                                         | 视频正在转码中，请重试                          |
| 31346 |                                                                         | 视频转码失败                               |
| 31347 |                                                                         | 当前视频太长，暂不支持在线播放                      |
| 31360 |                                                                         | url过期                                |
| 31362 |                                                                         | 签名错误，请检查链接地址是否完整。                    |
| 31363 |                                                                         | 分片缺失                                 |
| 31364 |                                                                         | 超出分片大小限制                             |
| 31390 |                                                                         | 音频被封禁                                |
| 31391 |                                                                         | 视频被封禁                                |
| 31400 | param error                                                             | 参数错误                                 |
| 31401 | malformed json                                                          | JSON格式错误                             |
| 31402 | no "table" in request                                                   | 请求中没有“table”字段                       |
| 31403 | no "records" in request                                                 | 请求中没有“records”字段                     |
| 31405 | too many records in request                                             | 请求中的records 过多，目前限制为500              |
| 31406 | bad columnname                                                          | 列名非法，请参考API文档                        |
| 31407 | record too large                                                        | record过大，> 1M                        |
| 31408 | bad table name                                                          | table名称不合法                           |
| 31409 | table not exist                                                         | table不存在，请先创建                        |
| 31410 | bad record                                                              | record格式错误，请检查JSON                   |
| 31411 | no appid                                                                | 请求中没有“app_id”字段                      |
| 31412 | no userid                                                               | 请求中没有“user_id”字段                     |
| 31420 | bad condition                                                           | condition描述错误。                       |
| 31421 | bad projection                                                          | projection描述错误                       |
| 31422 | bad order_by                                                            | order_by描述错误                         |
| 31423 | bad operator                                                            | condition中的operation 非法              |
| 31424 | bad start/limit                                                         | start/limit 错误                       |
| 31425 | unsupported operator                                                    | 操作符暂未支持，如：or、like、regex等             |
| 31430 | no key in record                                                        | update/delete 请求，但是record 中没有_key 字段 |
| 31431 | record not exist                                                        | 符合条件的record不存在，比如if-match不匹配、在回收站等   |
| 31432 | unknown op                                                              | 参数op非法                               |
| 31433 | bad key                                                                 | key非法                                |
| 31440 | param cursor not set                                                    | 参数cursor未设值                          |
| 31441 | param cursor format error                                               | 参数cursor格式错误                         |
| 31442 | param cursor appid wrong                                                | 参数cursor appid错误                     |
| 31443 | param cursor user_id wrong                                              | 参数cursor user_id错误                   |
| 31450 | exceed quota                                                            | 超出配额                                 |
| 31451 | quota size param not exist                                              | 找不到参数quota size                      |
| 31452 | quota info fail                                                         | quota info失败                         |
| 31453 | quota too big                                                           | quota过大                              |
| 31454 | quota size param not numberic                                           | quota size 参数未数值化                    |
| 31460 | no permission                                                           | 未授权                                  |
| 31461 | account not login                                                       | 账户为登录，使用bduss认证失败                    |
| 31462 | access token error                                                      | access token校验失败                     |
| 31470 | index num too much                                                      | index num太多                          |
| 31472 | table already exist                                                     | table已存在                             |
| 31473 | abnormal table already exist                                            | 异常table已存在                           |
| 31474 | table not drop, cannot restore                                          | table不在回收站，无法恢复                      |
| 31475 | engine not support                                                      | 不支持此项操作                              |
| 31480 | param op wrong, should be recycled or permanent                         | 参数op错误，应为可回收或永久的                     |
| 31490 | api not support                                                         | 调用了错误的API                            |
| 31500 | Internal error (Try Again Later)                                        | 内部错误                                 |
| 31501 | storeengine construct fail                                              | construct失败                          |
| 31502 | storeengine select fail                                                 | 选择操作失败                               |
| 31503 | storeengine insert fail                                                 | 插入操作失败                               |
| 31504 | storeengine update fail                                                 | 更新操作失败                               |
| 31505 | storeengine delete fail                                                 | 删除操作失败                               |
| 31506 | storeengine count fail                                                  | count操作失败                            |
| 31507 | storeengine ensure index fail                                           | 查询或创建索引失败                            |
| 31508 | storeengine delete index fail                                           | 删除索引失败                               |
| 31509 | storeengine drop table fail                                             | 删除table操作失败                          |
| 31530 | config set num match fail                                               | 配置中num匹配失败                           |
| 31590 | db query error                                                          | db交互出错                               |
| 31591 | network error                                                           | 内部网络交互错误                             |
| 36004 | no permission                                                           | 请重新登录再试                              |
| 42100 |                                                                         | 设备类型不存在                              |
| 42101 |                                                                         | 设备未注册                                |
| 42102 |                                                                         | 超过每个用户能绑定的最大设备个数限制                   |
| 42103 |                                                                         | 超过每个设备能绑定的最大用户个数限制                   |
| 42905 |                                                                         | 查询用户名失败，可重试                          |
| 42202 |                                                                         | 文件个数超过相册容量上限                         |
| 42203 |                                                                         | 相册不存                                 |
| 42210 |                                                                         | 部分文件添加失败                             |
| 42211 |                                                                         | 图片详细信息查询失败                           |
| 42212 |                                                                         | 共享目录文件上传者信息查询失败，可重试                  |
| 42213 |                                                                         | 共享目录鉴权失败                             |
| 42214 |                                                                         | 文件基础信息查询失败                           |
| 50002 |                                                                         | 播单id不存在                              |