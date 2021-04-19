rootProject.name = "Shards"

listOf("core").forEach {
  include(it)
  project(":$it").name = "shards-$it"
}
