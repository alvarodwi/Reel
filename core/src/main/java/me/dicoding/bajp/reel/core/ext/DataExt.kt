package me.dicoding.bajp.reel.utils.ext

// generate safe url from string
fun String?.toSafeUrl(
  scheme: String = "https"
): String {
  var url = this ?: ""
  if (url.contains("""\/""")) {
    url = url.replace("""\/""", "/")
  }
  return when {
    url.startsWith("http") -> url
    url.startsWith("//") -> "$scheme:$url"
    else -> url
  }
}