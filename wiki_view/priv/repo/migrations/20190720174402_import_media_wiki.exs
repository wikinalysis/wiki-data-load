defmodule WikiView.Repo.Migrations.ImportMediaWiki do
  use Ecto.Migration

  def up do
    execute File.read!(Path.expand("./priv/repo/migrations/20190720_mediawiki_schema.sql"))
  end

  def down do
    
  end
end
