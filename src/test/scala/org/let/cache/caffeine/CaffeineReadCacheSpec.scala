package org.let.cache.caffeine

import com.github.benmanes.caffeine.cache.CacheLoader
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}
import org.mockito.Mockito._
class CaffeineReadCacheSpec extends FlatSpec with Matchers with MockitoSugar {

  "CaffeineReadCache" should "get key test" in {
    val cacheLoader = mock[CacheLoader[String, String]]
    when(cacheLoader.load("test")).thenReturn("value")
    val caffeineReadCache = new CaffeineReadCache[String, String](cacheLoader)

    val value = caffeineReadCache.getOrLoad("test")
    assert(value == "value")

    val svalue = caffeineReadCache.getOrLoad("test")
    assert(svalue == "value")

    verify(cacheLoader,times(1)).load("test")

    val empty = caffeineReadCache.getOrLoad("empty")
    assert(empty == null)
  }

}
