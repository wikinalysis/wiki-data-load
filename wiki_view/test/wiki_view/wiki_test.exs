defmodule WikiView.WikiTest do
  use WikiView.DataCase

  alias WikiView.Wiki

  describe "page" do
    alias WikiView.Wiki.Page

    @valid_attrs %{
      content_model: "some content_model",
      is_new: "some is_new",
      is_redirect: "some is_redirect",
      language: "some lang",
      latest: "some latest",
      length: "some len",
      links_updated: "some links_updated",
      namespace: 42,
      random: "some random",
      restrictions: "some restrictions",
      title: "some title",
      touched: "some touched"
    }
    @update_attrs %{
      content_model: "some updated content_model",
      is_new: "some updated is_new",
      is_redirect: "some updated is_redirect",
      language: "some updated lang",
      latest: "some updated latest",
      length: "some updated len",
      links_updated: "some updated links_updated",
      namespace: 43,
      random: "some updated random",
      restrictions: "some updated restrictions",
      title: "some updated title",
      touched: "some updated touched"
    }
    @invalid_attrs %{
      content_model: nil,
      is_new: nil,
      is_redirect: nil,
      language: nil,
      latest: nil,
      length: nil,
      links_updated: nil,
      namespace: nil,
      random: nil,
      restrictions: nil,
      title: nil,
      touched: nil
    }

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

    @valid_attrs %{
      rev_comment: "some rev_comment",
      rev_content_format: "some rev_content_format",
      rev_content_model: "some rev_content_model",
      rev_deleted: "some rev_deleted",
      rev_id: "some rev_id",
      rev_len: "some rev_len",
      rev_minor_edit: "some rev_minor_edit",
      rev_page: "some rev_page",
      rev_parent_id: "some rev_parent_id",
      rev_sha1: "some rev_sha1",
      rev_text_id: "some rev_text_id",
      rev_timestamp: "some rev_timestamp",
      rev_user: "some rev_user",
      rev_user_text: "some rev_user_text"
    }
    @update_attrs %{
      rev_comment: "some updated rev_comment",
      rev_content_format: "some updated rev_content_format",
      rev_content_model: "some updated rev_content_model",
      rev_deleted: "some updated rev_deleted",
      rev_id: "some updated rev_id",
      rev_len: "some updated rev_len",
      rev_minor_edit: "some updated rev_minor_edit",
      rev_page: "some updated rev_page",
      rev_parent_id: "some updated rev_parent_id",
      rev_sha1: "some updated rev_sha1",
      rev_text_id: "some updated rev_text_id",
      rev_timestamp: "some updated rev_timestamp",
      rev_user: "some updated rev_user",
      rev_user_text: "some updated rev_user_text"
    }
    @invalid_attrs %{
      rev_comment: nil,
      rev_content_format: nil,
      rev_content_model: nil,
      rev_deleted: nil,
      rev_id: nil,
      rev_len: nil,
      rev_minor_edit: nil,
      rev_page: nil,
      rev_parent_id: nil,
      rev_sha1: nil,
      rev_text_id: nil,
      rev_timestamp: nil,
      rev_user: nil,
      rev_user_text: nil
    }

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
    @update_attrs %{
      old_flags: "some updated old_flags",
      old_id: "some updated old_id",
      old_text: "some updated old_text"
    }
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
