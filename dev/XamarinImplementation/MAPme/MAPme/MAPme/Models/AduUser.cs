using DAL.SQLite;

namespace MAPme.Models
{
    public class AduUser
    {
        [PrimaryKey, AutoIncrement]
        public int Id { get; set; }
        [NotNull, MaxLength(50)]
        public string Username { get; set; }
        [NotNull]
        public string PasswordHash { get; set; } // necessary?
        [NotNull, MaxLength(255)]
        public string Email { get; set; }
        [NotNull, MaxLength(50)]
        public string Adu { get; set; }
    }
}
