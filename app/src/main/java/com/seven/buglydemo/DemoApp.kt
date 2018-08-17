package com.seven.buglydemo

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants


class DemoApp : TinkerApplication(
    ShareConstants.TINKER_ENABLE_ALL,
    DemoApplicationLike::class.java.name,
    "com.tencent.tinker.loader.TinkerLoader",
    false)
{

}