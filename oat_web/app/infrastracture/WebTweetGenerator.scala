package infrastracture

import domain.tweet.{ITweetGenerator, Tweet}
import javax.inject.{Inject, Singleton}
import play.api.libs.ws.{WSClient, WSRequest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

@Singleton
class WebTweetGenerator @Inject()(ws: WSClient) extends ITweetGenerator {

  def generateTweet(twitterId: String): Either[String, Tweet] = {
    val request: WSRequest = ws.url("http://127.0.0.1:5000/generate_tweet/" + twitterId)
    val futureResult: Future[String] = request.get().map{
      response => (response.json \ "tweet").as[String]
    }
    val tweetText: String = Await.result(futureResult, Duration.Inf)
    return Right(Tweet.createNewTweet(tweetText))
  }

  def generateTweet2(twitterId: String): Tweet = {
    val request: WSRequest = ws.url("http://127.0.0.1:5000/generate_tweet/" + twitterId)
    val futureResult: Future[String] = request.get().map{
      response => (response.json \ "tweet").as[String]
    }
    val tweetText: String = Await.result(futureResult, Duration.Inf)
    return Tweet.createNewTweet(tweetText)
  }

}