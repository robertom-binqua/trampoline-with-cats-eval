package example

class RealFileSystemStackUnsafeFilesCounterSpec extends AbstractRealFileSystemFilesCounterSpec {
  override val filesCounterUnderTest: FilesCounter = FilesCounter.StackUnsafe(filesystem = FileSystems.realFileSystem)
}
