package example

import munit.FunSuite

class FileSystemSpec extends FunSuite {

  test("FakeFileSystem(withNumberOfFiles = 2) has the right files") {

    val fileSystem: FileSystems.FakeFileSystem = FileSystems.FakeFileSystem(withNumberOfFiles = 3)

    assertEquals(
      obtained = fileSystem.toFile("root").listFiles(),
      expected = List(
        fileSystem.makeFile("root/d1"),
        fileSystem.makeFile("root/f1")
      )
    )

    assertEquals(
      obtained = fileSystem.toFile("root/d1").listFiles(),
      expected = List(
        fileSystem.makeFile("root/d1/d2"),
        fileSystem.makeFile("root/d1/f2")
      )
    )

    assertEquals(
      obtained = fileSystem.toFile("root/d1/d2").listFiles(),
      expected = List(
        fileSystem.makeFile("root/d1/d2/f3")
      )
    )
  }

  test("root/d1 has the right files") {
    val fileSystem: FileSystems.FakeFileSystem = FileSystems.FakeFileSystem(withNumberOfFiles = 10)
    assertEquals(
      obtained = fileSystem.toFile("root/d1").listFiles(),
      expected = List(
        fileSystem.makeFile("root/d1/d2"),
        fileSystem.makeFile("root/d1/f2")
      )
    )
    assertEquals(
      obtained = fileSystem.toFile("root/d1/d2").listFiles(),
      expected = List(
        fileSystem.makeFile("root/d1/d2/d3"),
        fileSystem.makeFile("root/d1/d2/f3")
      )
    )

    assertEquals(
      obtained = fileSystem.toFile("root/d1/d2/d3").listFiles(),
      expected = List(
        fileSystem.makeFile("root/d1/d2/d3/d4"),
        fileSystem.makeFile("root/d1/d2/d3/f4")
      )
    )

  }

  test("A file is a file or a dir") {
    assertEquals(
      obtained = FileSystems.FakeFile(path = "root/d1/d2", withMaxNumberOfFiles = 1000).isDirectory,
      expected = true
    )

    assertEquals(
      obtained = FileSystems.FakeFile(path = "root/d1/f2", withMaxNumberOfFiles = 1000).isDirectory,
      expected = false
    )
  }

  test("root is a valid dir") {
    assertEquals(
      obtained = FileSystems.FakeFile(path = "root", withMaxNumberOfFiles = 1000).isDirectory,
      expected = true
    )
  }

}
