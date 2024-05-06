package example

object FileSystems {

  trait CommonFile {
    def getPath(): String
    def isDirectory: Boolean
    def listFiles(): List[CommonFile]
  }

  case class RealFile(nioPath: java.nio.file.Path) extends CommonFile {
    override def getPath(): String = nioPath.toFile.toString

    override def isDirectory: Boolean = nioPath.toFile.isDirectory

    override def listFiles(): List[CommonFile] = nioPath.toFile.listFiles().map(p => RealFile(p.toPath)).toList
  }

  case class FakeFile(path: String, withMaxNumberOfFiles: Int) extends CommonFile {
    private val theRootName = "root"
    private val isADir: Boolean = (path == theRootName || path.drop(1).reverse.takeWhile(_ != '/').last == 'd')
    private val maxNumberOfDirs = withMaxNumberOfFiles - 1
    private val actualIndex: Int = actualIndexOf(path)

    def getPath(): String = path

    def isDirectory: Boolean = isADir

    def listFiles(): List[CommonFile] = {
      if (actualIndex == maxNumberOfDirs)
        List(pathOf(s"f$withMaxNumberOfFiles"))
      else {
        val newIndex = actualIndex + 1
        List(
          pathOf(s"d$newIndex"),
          pathOf(s"f$newIndex")
        )
      }
    }

    private def pathOf(str: String): CommonFile = FakeFile(s"$path/$str", withMaxNumberOfFiles)

    private def actualIndexOf(path: String): Int = {
      if (path == theRootName) 0
      else
        path.split("/").toList.last.replaceFirst(if (isADir) "d" else "f", "").toInt
    }

  }

  trait FileSystem {
    def toFile(path: String): CommonFile
  }

  val realFileSystem: FileSystem = new FileSystem {
    override def toFile(path: String): CommonFile = RealFile(java.nio.file.Paths.get(path))
  }

  case class FakeFileSystem(withNumberOfFiles: Int) extends FileSystem {

    override def toFile(path: String): CommonFile = makeFile(path)

    def makeFile(path: String): FakeFile = FakeFile(path, withNumberOfFiles)

  }

}
