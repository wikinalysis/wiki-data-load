defmodule WikiView.Wiki.Revision do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  @derive {Jason.Encoder, except: [:__meta__]}
  schema "revision" do
    field :id, :integer, primary_key: true, source: :rev_id
    field :comment, :string, source: :rev_comment
    field :user, :integer, source: :rev_user
    field :user_text, :string, source: :rev_user_text
    field :timestamp, :string, source: :rev_timestamp
    field :minor_edit, :integer, source: :rev_minor_edit
    field :deleted, :integer, source: :rev_deleted
    field :length, :integer, source: :rev_len
    field :parent_id, :integer, source: :rev_parent_id
    field :sha1, :string, source: :rev_sha1
    field :content_model, :string, source: :rev_content_model
    field :content_format, :string, source: :rev_content_format

    belongs_to :page, WikiView.Wiki.Page,
      foreign_key: :page_id,
      references: :id,
      source: :rev_page

    belongs_to :text, WikiView.Wiki.Text,
      foreign_key: :text_id,
      references: :id,
      source: :rev_text_id
  end

  @attrs [
    :id,
    :page_id,
    :text_id,
    :comment,
    :user,
    :user_text,
    :timestamp,
    :minor_edit,
    :deleted,
    :length,
    :parent_id,
    :sha1,
    :content_model,
    :content_format
  ]

  @doc false
  def changeset(revision, attrs) do
    revision
    |> cast(attrs, @attrs)
  end
end
