package xyz.cssxsh.baidu.disk

object NetDiskApi {
    // Index
    const val INDEX_PAGE = "https://pan.baidu.com/"
    const val USER_AGENT = "pan.baidu.com"
    // Base
    const val API_QUOTA = "https://pan.baidu.com/api/quota"
    const val API_CATEGORY_INFO = "https://pan.baidu.com/api/categoryinfo"
    const val PAN_NAS = "https://pan.baidu.com/rest/2.0/xpan/nas"
    const val PAN_FILE = "https://pan.baidu.com/rest/2.0/xpan/file"
    const val PAN_MULTIMEDIA = "https://pan.baidu.com/rest/2.0/xpan/multimedia"
    const val PCS_SUPER_FILE = "https://pcs.baidu.com/rest/2.0/pcs/superfile2"
    const val PCS_FILE = "https://pcs.baidu.com/rest/2.0/pcs/file"

    /**
     * 256KB
     */
    const val SLICE_SIZE = 256 shl 10
}