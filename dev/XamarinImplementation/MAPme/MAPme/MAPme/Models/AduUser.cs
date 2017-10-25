using DAL.SQLite;

namespace MAPme.Models
{
    public class AduUser
    {
        [PrimaryKey, AutoIncrement]
        public int Id { get; set; }
        [NotNull]
        private string Username { get; set; }
        [NotNull]
        private string Email { get; set; }
        [NotNull]
        public string Adu { get; set; }
    }
}
