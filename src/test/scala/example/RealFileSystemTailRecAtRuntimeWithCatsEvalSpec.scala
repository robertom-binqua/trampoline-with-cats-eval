package example

class RealFileSystemTailRecAtRuntimeWithCatsEvalSpec extends AbstractRealFileSystemFilesCounterSpec {
  override val filesCounterUnderTest: FilesCounter = FilesCounter.TailRecAtRuntimeWithCatsEval(FileSystems.realFileSystem)
}
