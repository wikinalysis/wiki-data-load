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

    test "change_page/1 returns a page changeset" do
      page = page_fixture()
      assert %Ecto.Changeset{} = Wiki.change_page(page)
    end
  end

  describe "revisions" do
    alias WikiView.Wiki.Revision

    @valid_attrs %{rev_comment: "some rev_comment", rev_content_format: "some rev_content_format", rev_content_model: "some rev_content_model", rev_deleted: "some rev_deleted", rev_id: "some rev_id", rev_len: "some rev_len", rev_minor_edit: "some rev_minor_edit", rev_page: "some rev_page", rev_parent_id: "some rev_parent_id", rev_sha1: "some rev_sha1", rev_text_id: "some rev_text_id", rev_timestamp: "some rev_timestamp", rev_user: "some rev_user", rev_user_text: "some rev_user_text"}
    @update_attrs %{rev_comment: "some updated rev_comment", rev_content_format: "some updated rev_content_format", rev_content_model: "some updated rev_content_model", rev_deleted: "some updated rev_deleted", rev_id: "some updated rev_id", rev_len: "some updated rev_len", rev_minor_edit: "some updated rev_minor_edit", rev_page: "some updated rev_page", rev_parent_id: "some updated rev_parent_id", rev_sha1: "some updated rev_sha1", rev_text_id: "some updated rev_text_id", rev_timestamp: "some updated rev_timestamp", rev_user: "some updated rev_user", rev_user_text: "some updated rev_user_text"}
    @invalid_attrs %{rev_comment: nil, rev_content_format: nil, rev_content_model: nil, rev_deleted: nil, rev_id: nil, rev_len: nil, rev_minor_edit: nil, rev_page: nil, rev_parent_id: nil, rev_sha1: nil, rev_text_id: nil, rev_timestamp: nil, rev_user: nil, rev_user_text: nil}

    def revision_fixture(attrs \\ %{}) do
      {:ok, revision} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Wiki.create_revision()

      revision
    end

    test "list_revisions/0 returns all revisions" do
      revision = revision_fixture()
      assert Wiki.list_revisions() == [revision]
    end

    test "get_revision!/1 returns the revision with given id" do
      revision = revision_fixture()
      assert Wiki.get_revision!(revision.id) == revision
    end

    test "change_revision/1 returns a revision changeset" do
      revision = revision_fixture()
      assert %Ecto.Changeset{} = Wiki.change_revision(revision)
    end
  end

  describe "texts" do
    alias WikiView.Wiki.Text

    @valid_attrs %{old_flags: "some old_flags", old_id: "some old_id", old_text: "some old_text"}
    @update_attrs %{old_flags: "some updated old_flags", old_id: "some updated old_id", old_text: "some updated old_text"}
    @invalid_attrs %{old_flags: nil, old_id: nil, old_text: nil}

    def text_fixture(attrs \\ %{}) do
      {:ok, text} =
        attrs
        |> Enum.into(@valid_attrs)
        |> Wiki.create_text()

      text
    end

    test "list_texts/0 returns all texts" do
      text = text_fixture()
      assert Wiki.list_texts() == [text]
    end

    test "get_text!/1 returns the text with given id" do
      text = text_fixture()
      assert Wiki.get_text!(text.id) == text
    end

    test "change_text/1 returns a text changeset" do
      text = text_fixture()
      assert %Ecto.Changeset{} = Wiki.change_text(text)
    end
  end
end
