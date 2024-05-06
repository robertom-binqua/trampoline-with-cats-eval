package example

import munit.FunSuite

abstract class AbstractRealFileSystemFilesCounterSpec extends FunSuite {

  val filesCounterUnderTest: FilesCounter

  test("dir-with-only-empty-dirs") {
    assertEquals(obtained = filesCounterUnderTest.countFilesIn("src/test/resources/dir-with-only-empty-dirs"), expected = 0)
  }

  test("dir-with-2-files") {
    assertEquals(obtained = filesCounterUnderTest.countFilesIn("src/test/resources/dir-with-2-files"), expected = 2)
  }

  test("dir-with-1-file") {
    assertEquals(obtained = filesCounterUnderTest.countFilesIn("src/test/resources/dir-with-1-file"), expected = 1)
  }

  test("dir-with-2-files-and-1-empty-dir") {
    assertEquals(
      obtained = filesCounterUnderTest.countFilesIn("src/test/resources/dir-with-2-files-and-1-empty-dir"),
      expected = 2
    )
  }

}
