defmodule WikiView.Wiki.Page do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  @derive {Jason.Encoder, except: [:__meta__, :revision, :text]}
  schema "page" do
    field :id, :integer, primary_key: true, source: :page_id
    field :namespace, :integer, source: :page_namespace
    field :title, :string, source: :page_title
    field :restrictions, :string, source: :page_restrictions
    field :is_redirect, :integer, source: :page_is_redirect
    field :is_new, :integer, source: :page_is_new
    field :random, :float, source: :page_random
    field :touched, :string, source: :page_touched
    field :links_updated, :string, source: :page_links_updated
    field :length, :integer, source: :page_len
    field :content_model, :string, source: :page_content_model
    field :language, :string, source: :page_lang

    belongs_to :revision, WikiView.Wiki.Revision,
      foreign_key: :latest,
      references: :id,
      source: :page_latest

    has_one :text, through: [:revision, :text], references: :id

    has_many :revisions, WikiView.Wiki.Revision, foreign_key: :page_id, references: :id
  end

  @attrs [
    :namespace,
    :title,
    :restrictions,
    :is_redirect,
    :is_new,
    :random,
    :touched,
    :links_updated,
    :latest,
    :length,
    :content_model,
    :language
  ]

  @doc false
  def changeset(page, attrs) do
    page
    |> cast(attrs, @attrs)
  end
end
