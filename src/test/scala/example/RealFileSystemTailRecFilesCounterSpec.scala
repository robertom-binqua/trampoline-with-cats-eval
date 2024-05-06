package example

class RealFileSystemTailRecFilesCounterSpec extends AbstractRealFileSystemFilesCounterSpec {
  override val filesCounterUnderTest: FilesCounter = FilesCounter.TailRec(FileSystems.realFileSystem)
}
