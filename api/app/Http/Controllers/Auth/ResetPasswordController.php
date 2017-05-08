<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\ResetsPasswords;
use Illuminate\Support\Facades\Password;

class ResetPasswordController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Password Reset Controller
    |--------------------------------------------------------------------------
    |
    | This controller is responsible for handling password reset requests
    | and uses a simple trait to include this behavior. You're free to
    | explore this trait and override any methods you wish to tweak.
    |
    */

    use ResetsPasswords;

    /**
     * Where to redirect users after resetting their password.
     *
     * @var string
     */
    protected $redirectTo = '/home';

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    protected function rules()
    {
        return [
            'email' => 'required|email',
            'password' => 'required|confirmed|min:6',
        ];
    }

    protected function credentials(Request $request)
    {
        return $request->only(
            'email', 'password', 'password_confirmation'
        );
    }

    public function resetForm(Request $request)
    {
        return view('auth.passwords.reset')->with(
            ['email' => Auth::user()->email]
        );
    }

    public function reset(Request $request)
    {
        $this->validate($request, $this->rules(), $this->validationErrorMessages());

        $this->resetPassword(Auth::user(), $request->input('password'));
        return redirect($this->redirectPath());
    }
}
