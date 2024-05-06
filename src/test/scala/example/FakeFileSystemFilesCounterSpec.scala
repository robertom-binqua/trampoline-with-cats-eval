package example

import munit.FunSuite

class FakeFileSystemFilesCounterSpec extends FunSuite {

  private val maxNumberOfNestedDirs = 5000

  test("MARKED AS IGNORE!!! .... StackUnsafe countFilesIn root/d1,f1/d2,f2/....../d4999,f4999/f5000".ignore) {
    assertEquals(
      obtained = FilesCounter
        .StackUnsafe(filesystem = FileSystems.FakeFileSystem(withNumberOfFiles = maxNumberOfNestedDirs))
        .countFilesIn("root"),
      expected = maxNumberOfNestedDirs
    )
  }

  test("TailRec countFilesIn root/d1,f1/d2,f2/....../d4999,f4999/f5000") {
    assertEquals(
      obtained = FilesCounter
        .TailRec(FileSystems.FakeFileSystem(2))
        .countFilesIn("root"),
      expected = 2
    )
  }

  test("TailRecAtRuntimeWithCatsEval countFilesIn root/d1,f1/d2,f2/....../d4999,f4999/f5000") {
    assertEquals(
      obtained = FilesCounter
        .TailRecAtRuntimeWithCatsEval(FileSystems.FakeFileSystem(withNumberOfFiles = maxNumberOfNestedDirs))
        .countFilesIn("root"),
      expected = maxNumberOfNestedDirs
    )
  }

}
