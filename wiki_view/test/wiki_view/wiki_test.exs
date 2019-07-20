defmodule WikiView.WikiTest do
  use WikiView.DataCase

  alias WikiView.Wiki

  describe "page" do
    alias WikiView.Wiki.Page

    @valid_attrs %{page_content_model: "some page_content_model", page_is_new: "some page_is_new", page_is_redirect: "some page_is_redirect", page_lang: "some page_lang", page_latest: "some page_latest", page_len: "some page_len", page_links_updated: "some page_links_updated", page_namespace: 42, page_random: "some page_random", page_restrictions: "some page_restrictions", page_title: "some page_title", page_touched: "some page_touched"}
    @update_attrs %{page_content_model: "some updated page_content_model", page_is_new: "some updated page_is_new", page_is_redirect: "some updated page_is_redirect", page_lang: "some updated page_lang", page_latest: "some updated page_latest", page_len: "some updated page_len", page_links_updated: "some updated page_links_updated", page_namespace: 43, page_random: "some updated page_random", page_restrictions: "some updated page_restrictions", page_title: "some updated page_title", page_touched: "some updated page_touched"}
    @invalid_attrs %{page_content_model: nil, page_is_new: nil, page_is_redirect: nil, page_lang: nil, page_latest: nil, page_len: nil, page_links_updated: nil, page_namespace: nil, page_random: nil, page_restrictions: nil, page_title: nil, page_touched: nil}

    def page_fixture(attrs \\ %{}) do
      {:ok, page} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Wiki.create_page()

      page
    end

    test "list_page/0 returns all page" do
      page = page_fixture()
      assert Wiki.list_page() == [page]
    end

    test "get_page!/1 returns the page with given id" do
      page = page_fixture()
      assert Wiki.get_page!(page.id) == page
    end

    test "create_page/1 with valid data creates a page" do
      assert {:ok, %Page{} = page} = Wiki.create_page(@valid_attrs)
      assert page.page_content_model == "some page_content_model"
      assert page.page_is_new == "some page_is_new"
      assert page.page_is_redirect == "some page_is_redirect"
      assert page.page_lang == "some page_lang"
      assert page.page_latest == "some page_latest"
      assert page.page_len == "some page_len"
      assert page.page_links_updated == "some page_links_updated"
      assert page.page_namespace == 42
      assert page.page_random == "some page_random"
      assert page.page_restrictions == "some page_restrictions"
      assert page.page_title == "some page_title"
      assert page.page_touched == "some page_touched"
    end

    test "create_page/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Wiki.create_page(@invalid_attrs)
    end

    test "update_page/2 with valid data updates the page" do
      page = page_fixture()
      assert {:ok, %Page{} = page} = Wiki.update_page(page, @update_attrs)
      assert page.page_content_model == "some updated page_content_model"
      assert page.page_is_new == "some updated page_is_new"
      assert page.page_is_redirect == "some updated page_is_redirect"
      assert page.page_lang == "some updated page_lang"
      assert page.page_latest == "some updated page_latest"
      assert page.page_len == "some updated page_len"
      assert page.page_links_updated == "some updated page_links_updated"
      assert page.page_namespace == 43
      assert page.page_random == "some updated page_random"
      assert page.page_restrictions == "some updated page_restrictions"
      assert page.page_title == "some updated page_title"
      assert page.page_touched == "some updated page_touched"
    end

    test "update_page/2 with invalid data returns error changeset" do
      page = page_fixture()
      assert {:error, %Ecto.Changeset{}} = Wiki.update_page(page, @invalid_attrs)
      assert page == Wiki.get_page!(page.id)
    end

    test "delete_page/1 deletes the page" do
      page = page_fixture()
      assert {:ok, %Page{}} = Wiki.delete_page(page)
      assert_raise Ecto.NoResultsError, fn -> Wiki.get_page!(page.id) end
    end

    test "change_page/1 returns a page changeset" do
      page = page_fixture()
      assert %Ecto.Changeset{} = Wiki.change_page(page)
    end
  end
end
