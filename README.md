# trampoline-with-cats-eval
How Cats Eval give us tailRec for free

example.RealFileSystemStackUnsafeFilesCounterSpec:
+ dir-with-only-empty-dirs 0.024s
+ dir-with-2-files 0.001s
+ dir-with-1-file 0.0s
+ dir-with-2-files-and-1-empty-dir 0.001s
  example.RealFileSystemTailRecFilesCounterSpec:
+ dir-with-only-empty-dirs 0.024s
+ dir-with-2-files 0.001s
+ dir-with-1-file 0.0s
+ dir-with-2-files-and-1-empty-dir 0.001s
  example.FileSystemSpec:
+ FakeFileSystem(withNumberOfFiles = 2) has the right files 0.024s
+ root/d1 has the right files 0.001s
+ A file is a file or a dir 0.0s
+ root is a valid dir 0.0s
  example.ExampleSpec:
+ Counters.count: we can sum 20000 ones with Eval 0.037s
+ Example.Counter.count(List(1,2,3,4)), 10 0.002s
+ countWithStackOverflow(List(1,2,3,4)), 10 0.0s
  example.RealFileSystemTailRecAtRuntimeWithCatsEvalSpec:
+ dir-with-only-empty-dirs 0.146s
+ dir-with-2-files 0.001s
+ dir-with-1-file 0.0s
+ dir-with-2-files-and-1-empty-dir 0.0s
  example.FakeFileSystemFilesCounterSpec:
  ==> i example.FakeFileSystemFilesCounterSpec.MARKED AS IGNORE!!! .... StackUnsafe countFilesIn root/d1,f1/d2,f2/....../d4999,f4999/f5000 ignored 0.001s
+ TailRec countFilesIn root/d1,f1/d2,f2/....../d4999,f4999/f5000 0.01s
+ TailRecAtRuntimeWithCatsEval countFilesIn root/d1,f1/d2,f2/....../d4999,f4999/f5000 1.285s