defmodule WikiViewWeb.WikiApiViewTest do
  use WikiViewWeb.ConnCase, async: true

  @example_page %WikiView.Wiki.Page{
    id: 1,
    is_new: 0,
    is_redirect: 1,
    language: 0,
    latest: 0,
    random: 0,
    restrictions: 0,
    title: 0,
    touched: "20190721003440",
    links_updated: 0,
    restrictions: 0,
    namespace: 0,
    content_model: "wikitext",
    text: %Ecto.Association.NotLoaded{},
    revision: %Ecto.Association.NotLoaded{}
  }

  test "page_json/1 transforms a page struct w/o associations" do
    assert %{
             revisions: nil,
             id: 1,
             is_new: false,
             is_redirect: true,
             language: 0,
             latest: 0,
             random: 0,
             restrictions: 0,
             title: 0,
             touched: ~N[2019-07-21 00:34:40],
             links_updated: 0,
             restrictions: 0,
             namespace: 0,
             content_model: "wikitext"
           } = WikiViewWeb.WikiApiView.page_json(@example_page)
  end
end
