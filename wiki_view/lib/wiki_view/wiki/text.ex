defmodule WikiView.Wiki.Text do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  schema "texts" do
    field :id, :string, primary_key: true, source: :old_id
    field :text, :string, source: :old_text
    field :flags, :string, source: :old_flags
  end

  @doc false
  def changeset(text, attrs) do
    text
    |> cast(attrs, [:id, :text, :lags])
    |> validate_required([:id, :text, :flags])
  end
end
