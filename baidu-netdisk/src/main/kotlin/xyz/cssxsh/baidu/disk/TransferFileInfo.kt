package xyz.cssxsh.baidu.disk


/**
 * 合并信息
 * @param shareId 分享者ID 在 [BaiduNetDiskRESTful.view] 中得到的 shareId
 * @param from 分享者ID 在 [BaiduNetDiskRESTful.view] 中得到的 uk
 * @param key 在 [BaiduNetDiskRESTful.verify] 中得到的 key
 * @param files 要转存的文件 fid 列表
 */
public data class TransferFileInfo(
    val shareId: Long,
    val from: Long,
    val key: String,
    val files: List<Long>
)