package example

import cats.Eval
import cats.implicits.toTraverseOps
import example.FileSystems.CommonFile

import scala.annotation.tailrec

trait FilesCounter {
  def countFilesIn(rawPath: String): Int
}

object FilesCounter {

  case class StackUnsafe(filesystem: FileSystems.FileSystem) extends FilesCounter {
    override def countFilesIn(rawPath: String): Int = {
      val (dirs, files) = filesystem.toFile(rawPath).listFiles().partition(_.isDirectory)
      files.length + dirs.map(dir => countFilesIn(dir.getPath())).sum
      // shortly you can see the line above will change into something like
      // .... tailRec(the caller found files.length files ... , the caller found d1,d2,d3.. )
      // ... tailRec please I did the job to find something and I did some maths ... please you carry on.
    }
  }

  case class TailRec(filesystem: FileSystems.FileSystem) extends FilesCounter {

    override def countFilesIn(rawPath: String): Int =
      tailrec(countedSoFar = 0, dirsLeft = List(filesystem.toFile(rawPath)))

    @tailrec private def tailrec(countedSoFar: Int, dirsLeft: List[CommonFile]): Int = {
      dirsLeft match {
        case Nil => countedSoFar //mission accomplished
        case aDir :: dirsLeftWithoutHead =>
          val (dirsFoundNow, filesFoundNow) = aDir.listFiles().partition(_.isDirectory)
          tailrec(countedSoFar + filesFoundNow.length, dirsLeft = dirsFoundNow ::: dirsLeftWithoutHead)
      }
    }
  }

  case class TailRecAtRuntimeWithCatsEval(filesystem: FileSystems.FileSystem) extends FilesCounter {
    override def countFilesIn(dirPath: String): Int = countFilesWithEval(dirPath).value

    private def countFilesWithEval(rawPath: String): Eval[Int] =
      Eval
        .now(filesystem.toFile(rawPath).listFiles().partition(_.isDirectory))
        .flatMap({ case (dirs, files) =>
          val resultsForAllDirs: List[Eval[Int]] =
            dirs.map(dir => Eval.defer(countFilesWithEval(dir.getPath())))
          val result: Eval[List[Int]] = resultsForAllDirs.sequence
          result.map(countedFiles => countedFiles.sum + files.length)
        })
  }


}
