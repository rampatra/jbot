package mary.github

import org.kohsuke.github.GHIssueState
import org.kohsuke.github.GHPullRequest
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHubBuilder


class GitClient {
    private val gitHub = GitHubBuilder()
        .withOAuthToken("5a03572526128db17d2f516210a0da610c534482")
        .build()

    fun getCreatedPullRequests(user: String): List<LogzioPR> {
        return getRepositories()
            .map { it.getPullRequests(GHIssueState.OPEN) }
            .flatten()
            .filter { it.user.login == user }
            .map { mapToLogzioPR(it) }
            .toList()
    }

    fun getAssignedPullRequests(user: String): List<LogzioPR> {
        return getRepositories()
            .map { it.getPullRequests(GHIssueState.OPEN) }
            .flatten()
            .filter { it.assignees.map { user -> user.login }.toList().contains(user) }
            .map { mapToLogzioPR(it) }
            .toList()
    }

    fun assignReviewerToPullRequest(pr: LogzioPR, assigneeUser: String) {
        val repository = gitHub.getRepository("logzio/${pr.repoName}")
        val pullRequest = repository.getPullRequests(GHIssueState.OPEN)
            .firstOrNull { it.id == pr.id } ?: throw RuntimeException("can't find PR")


        pullRequest.assignTo(gitHub.getUser(assigneeUser))
    }

    fun getRepositories() : List<GHRepository> {
        return listOf(gitHub.getRepository("logzio/gaia-full"));
    }

    private fun mapToLogzioPR(pullRequest: GHPullRequest) : LogzioPR {
        return LogzioPR(
            pullRequest.number, pullRequest.id, pullRequest.title, pullRequest.repository.name,
            pullRequest.head.label, pullRequest.additions,
            pullRequest.deletions, pullRequest.assignee?.login ?: ""
        )
    }
}

data class LogzioPR(
    val number: Int,
    val id: Long,
    val title: String,
    val repoName: String,
    val branch: String,
    val addedLines: Int,
    val removedLines: Int,
    val assignee: String = ""
)
