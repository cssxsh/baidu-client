package xyz.cssxsh.baidu.disk

public const val INDEX_PAGE: String = "https://pan.baidu.com/"
public const val USER_AGENT: String = "pan.baidu.com"

// API
public const val API_QUOTA: String = "https://pan.baidu.com/api/quota"
public const val API_CATEGORY_INFO: String = "https://pan.baidu.com/api/categoryinfo"
public const val API_CREATE: String = "https://pan.baidu.com/api/create"
public const val API_RAPID_UPLOAD: String = "https://pan.baidu.com/api/rapidupload"
public const val API_LIST: String = "https://pan.baidu.com/api/list"

// PAN
public const val PAN_NAS: String = "https://pan.baidu.com/rest/2.0/xpan/nas"
public const val PAN_FILE: String = "https://pan.baidu.com/rest/2.0/xpan/file"
public const val PAN_MULTIMEDIA: String = "https://pan.baidu.com/rest/2.0/xpan/multimedia"

// PCS
public const val PCS_SUPER_FILE: String = "https://pcs.baidu.com/rest/2.0/pcs/superfile2"
public const val PCS_FILE: String = "https://pcs.baidu.com/rest/2.0/pcs/file"
public const val PCS_QUOTA: String = "https://pcs.baidu.com/rest/2.0/pcs/quota?method=info"

/**
 * 256KB
 */
public const val SLICE_SIZE: Int = 256 shl 10

public val LAZY_BLOCKS: List<String> = listOf("5910a591dd8fc18c32a8f3df4fdc1761","a5fc157d78e6ad1c7e114b056c92821e")