package com.otitan.model

/**
 * 所有最新数据
 */
data class AllNewDataModel(val zdqxz_ln_wd: List<Zdqxzln>, val zdqxz_ln_sd: List<Zdqxzln>
                           , val zdqxz_ln_fs: List<Zdqxzln>, val zdqxz_lw_wd: List<Zdqxzlwwd>
                           , val zdqxz_lw_sd: List<Zdqxzlwsd>, val zdqxz_lw_fs: List<Zdqxzlwfs>
                           , val kqzl: List<Kqzl>, val fylz: List<Fylz>
                           , val ydpy_jyl: List<Ydpyjyl>, val ydpy_njd: List<Ydpynjd>
                           , val ydpy_jyqd: List<Ydpyjyqd>, val trss_ss: List<Trssss>
                           , val trss_wd: List<Trsswd>, val _ls_sgjl: List<Sgjl>) {
    data class Zdqxzln(val Five_m: String, val Ten_m: String,
                         val Fifteen_m: String, val Twenty_m: String, val Twenty_Five_m: String,
                         val Thirty_m: String, val Company: String)

    data class Zdqxzlnsd(val Five_m: String, val Ten_m: String,
                         val Fifteen_m: String, val Twenty_m: String, val Twenty_Five_m: String,
                         val Thirty_m: String, val Company: String)

    data class Zdqxzlnfs(val Five_m: String, val Ten_m: String,
                         val Fifteen_m: String, val Twenty_m: String, val Twenty_Five_m: String,
                         val Thirty_m: String, val Company: String)

    data class Zdqxzlwwd(val lw_wd: String, val Company: String)

    data class Zdqxzlwsd(val lw_sd: String, val Company: String)

    data class Zdqxzlwfs(val lw_fs: String, val Company: String)

    data class Kqzl(val PM25: String, val Company: String)

    data class Fylz(val FYLZ: String, val Company: String)

    data class Ydpyjyl(val jyl: String, val Company: String)

    data class Ydpynjd(val njd: String, val Company: String)

    data class Ydpyjyqd(val jyqd: String, val Company: String)

    data class Trssss(val trss_ss: String, val Company: String)

    data class Trsswd(val trss_wd: String, val Company: String)

    data class Sgjl(val TDP_FLOW1: String, val TDP_FLOW2: String
                    , val TDP_FLOW3: String, val TDP_FLOW4: String, val TDP_FLOW5: String
                    , val TDP_FLOW6: String, val TDP_FLOW7: String, val TDP_FLOW8: String
                    , val TDP_FLOW9: String, val TDP_FLOW10: String, val TDP_FLOW11: String
                    , val TDP_FLOW12: String, val Company: String)
}