defmodule WikiView.Wiki.Revision do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  schema "revisions" do
    field :id, :string, primary_key: true, source: :rev_id
    field :page, :string, source: :rev_page
    field :text_id, :string, source: :rev_text_id
    field :comment, :string, source: :rev_comment
    field :user, :string, source: :rev_user
    field :user_text, :string, source: :rev_user_text
    field :timestamp, :string, source: :rev_timestamp
    field :minor_edit, :string, source: :rev_minor_edit
    field :deleted, :string, source: :rev_deleted
    field :len, :string, source: :rev_len
    field :parent_id, :string, source: :rev_parent_id
    field :sha1, :string, source: :rev_sha1
    field :content_model, :string, source: :rev_content_model
    field :content_format, :string, source: :rev_content_format
  end

  @doc false
  def changeset(revision, attrs) do
    revision
    |> cast(attrs, [:id, :page, :text_id, :comment, :user, :user_text, :timestamp, :minor_edit, :deleted, :len, :parent_id, :sha1, :content_model, :content_format])
    |> validate_required([:id, :page, :text_id, :comment, :user, :user_text, :timestamp, :minor_edit, :deleted, :len, :parent_id, :sha1, :content_model, :content_format])
  end
end
