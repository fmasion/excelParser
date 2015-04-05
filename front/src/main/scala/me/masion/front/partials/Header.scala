package me.masion.front.partials

import scalatags.JsDom.all._
import rx._
import scalatags.rx.all._

/**
 * Created by fred on 02/04/15.
 */
trait Header {

  def header = {
    div(cls:="header")(
      div(cls:="main-header")(
        div(cls:="left-nav")(i(cls:="logicon")),
        div(cls:="button-container pull-right")(
          a(href:="https://github.com/fmasion", target:="_blank", cls:="btn btn-github")(i( cls:="icon-github")( "Fork Me on Github"))
        ),
        div(cls:="title-doc")(
          "Incredible Fred Spreadsheet" //,
          //span( cls:="editable", data-url:="")("Titre")
        ),
        div(cls:="main-menu")(
          ul(cls:="list-nav")(
            li( a( href:="javascript:void(0)")("Fichier")),
            li( a( href:="javascript:void(0)")("Edition")),
            li( a( href:="javascript:void(0)")("Affichage")),
            li( a( href:="javascript:void(0)")("Insertion")),
            li( a( href:="javascript:void(0)")("Format")),
            li( a( href:="javascript:void(0)")("Données")),
            li( a( href:="javascript:void(0)")("Aide"))
          )
        )
      ),
      div(cls:="icon-nav")(
        ul( cls:="list-icon")(
          li( a(href:="javascript:void(0)")(i( cls:="icon-ptint"))),
          li( a(href:="javascript:void(0)")(i( cls:="icon-reply-mail"))),
          li( a(href:="javascript:void(0)")(i( cls:="icon-mail-forward"))),
          li( cls:="separator"),
          li( a( href:="javascript:void(0)")(i( cls:="icon-euro"))),
            li( cls:="separator"),
          li( a( href:="javascript:void(0)")(i( cls:="icon-bold"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-underline"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-strikethrough"))),
            li( cls:="separator"),
          li( a( href:="javascript:void(0)")(i( cls:="icon-align-left"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-align-center"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-align-right"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-align-justify"))),
            li( cls:="separator"),
          li( a( href:="javascript:void(0)")(i( cls:="icon-filter"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-sort-alpha-two"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-sort-alpha"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-sort-numeric-two"))),
          li( a( href:="javascript:void(0)")(i( cls:="icon-sort-numeric"))),
          li( cls:="separator"),
          li( a( href:="javascript:void(0)")(i( cls:="icon-bar-chart")))
        )
      ),
      div(cls:="function-bar")(
        span(cls:="icon-function"),
        input(`type`:="text")
      )
    )

  }

}
