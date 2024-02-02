java_library(
    name = "clientlib",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:com_squareup_okhttp3_okhttp",  # OkHttp
        "@maven//:com_squareup_okhttp3_okhttp_urlconnection",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "//src/main/java/com/algotrader/dataclasses:datalib"
    ],
)