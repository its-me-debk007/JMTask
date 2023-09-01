package com.example.jmtask.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.jmtask.model.Result

const val MOVIE_DB = "MovieDB"
const val NO_INTERNET = "No Internet Connection"

fun hideKeyboard(view: View, activity: Activity?) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

val favouritesList = mutableListOf<Result>(
    Result(
        false,
        "/4HodYYKEIsGOdinkGi2Ucz6X9i0.jpg",
        569094,
        "en",
        "Spider-Man: Across the Spider-Verse",
        "After reuniting with Gwen Stacy, Brooklyn’s full-time, friendly neighborhood Spider-Man is catapulted across the Multiverse, where he encounters the Spider Society, a team of Spider-People charged with protecting the Multiverse’s very existence. But when the heroes clash on how to handle a new threat, Miles finds himself pitted against the other Spiders and must set out on his own to save those he loves most.",
        1153.271,
        "/8Vt6mWEReuy4Of61Lnj5Xj704m8.jpg",
        "2023-05-31",
        "Spider-Man: Across the Spider-Verse",
        false,
        8.5,
        3886
    ),
    Result(
        false,
        "/yF1eOkaYvwiORauRCPWznV9xVvi.jpg",
        298618,
        "en",
        "The Flash",
        "When his attempt to save his family inadvertently alters the future, Barry Allen becomes trapped in a reality in which General Zod has returned and there are no Super Heroes to turn to. In order to save the world that he is in and return to the future that he knows, Barry's only hope is to race for his life. But will making the ultimate sacrifice be enough to reset the universe?",
        1048.107,
        "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
        "2023-06-13",
        "The Flash",
        false,
        6.9,
        2610
    ),
    Result(
        false,
        "/axIU0Ay88ZSfZHL5AlsQm64Bcb8.jpg",
        1121575,
        "en",
        "Babylon 5: The Road Home",
        "Travel across the galaxy with John Sheridan as he unexpectedly finds himself transported through multiple timelines and alternate realities in a quest to find his way back home. Along the way he reunites with some familiar faces, while discovering cosmic new revelations about the history, purpose, and meaning of the Universe.",
        1153.271,
        "/qlXLiFKf2kvJ4K2VDBC5Z048vm3.jpg",
        "2023-08-15",
        "Babylon 5: The Road Home",
        false,
        6.9,
        43
    ),
    Result(
        false,
        "/hPcP1kv6vrkRmQO3YgV1H97FE5Q.jpg",
        614479,
        "en",
        "Insidious: The Red Door",
        "To put their demons to rest once and for all, Josh Lambert and a college-aged Dalton Lambert must go deeper into The Further than ever before, facing their family's dark past and a host of new and more horrifying terrors that lurk behind the red door.",
        1153.271,
        "/d07phJqCx6z5wILDYqkyraorDPi.jpg",
        "2023-07-05",
        "Insidious: The Red Door",
        false,
        6.9,
        993
    ),
    Result(
        false,
        "/AqLcLsGGTzAjm3pCCq0CZCQrp6m.jpg",
        12444,
        "en",
        "Harry Potter and the Deathly Hallows: Part 1",
        "Harry, Ron and Hermione walk away from their last year at Hogwarts to find and destroy the remaining Horcruxes, putting an end to Voldemort's bid for immortality. But with Harry's beloved Dumbledore dead and Voldemort's unscrupulous Death Eaters on the loose, the world is more dangerous than ever.",
        1153.271,
        "/iGoXIpQb7Pot00EEdwpwPajheZ5.jpg",
        "2010-10-06",
        "Harry Potter and the Deathly Hallows: Part 1",
        false,
        7.7,
        993
    )
)
