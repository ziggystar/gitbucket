package gitbucket.core.api

import gitbucket.core.util.JGitUtil.{CommitInfo, TagInfo}
import gitbucket.core.util.RepositoryName
import org.eclipse.jgit.lib.Ref

case class ApiRefCommit(
  sha: String,
  `type`: String,
  url: String
)

case class ApiRef(
  ref: String,
  node_id: String = "",
  url: String,
  `object`: ApiRefCommit,
)

object ApiRef {

  def fromRef(
    repositoryName: RepositoryName,
    commit: Ref
  ): ApiRef =
    ApiRef(
      ref = commit.getName,
      url = ApiPath(s"/${repositoryName.fullName}/refs/${commit.getName}").path,
      `object` = ApiRefCommit(
        sha = commit.getObjectId.getName,
        url = ApiPath(s"/${repositoryName.fullName}/commits/${commit.getObjectId.getName}").path,
        `type` = "commit"
      )
    )

  def fromTag(
    repositoryName: RepositoryName,
    tagInfo: TagInfo
  ): ApiRef =
    ApiRef(
      ref = s"refs/tags/${tagInfo.name}",
      url = ApiPath(s"/${repositoryName.fullName}/refs/tags/${tagInfo.name}").toString,
      `object` = ApiRefCommit(
        sha = tagInfo.id,
        url = ApiPath(s"/${repositoryName.fullName}/tags/${tagInfo.id}").toString,
        `type` = "commit"
      )
    )
}
