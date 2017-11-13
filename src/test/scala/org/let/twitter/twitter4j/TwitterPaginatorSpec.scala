package org.let.twitter.twitter4j

import org.scalatest.{FlatSpec, Matchers}

class TwitterPaginatorSpec extends FlatSpec with Matchers {

  val twitter4JPaginator = new Twitter4JPaginator()
  val PageSize = 100

  "twitter4JPaginator" should "less than PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 10)
    assert(pageSize == 10)
  }

  it should "calculate 100 PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 100)
    assert(pageSize == PageSize)
  }

  it should "calculate 101 PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 101)
    assert(pageSize == 51)
  }

  it should "calculate 102 than PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 102)
    assert(pageSize == 51)
  }

  it should "calculate 103 PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 103)
    assert(pageSize == 52)
  }

  it should "calculate 200 PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 200)
    assert(pageSize == PageSize)
  }

  it should "calculate 201 PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 201)
    assert(pageSize == 67)
  }

  it should "calculate 203 PageSize" in {
    val pageSize = twitter4JPaginator.calculatePageSize(PageSize, 203)
    assert(pageSize == 68)
  }

}
